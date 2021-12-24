package com.mvg.sky.account.service.account;

import com.mvg.sky.account.dto.request.AccountProfileCreationRequest;
import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.account.dto.response.AccountProfileCreationResponse;
import com.mvg.sky.account.dto.response.LoginResponse;
import com.mvg.sky.account.security.UserPrincipal;
import com.mvg.sky.account.service.session.SessionService;
import com.mvg.sky.account.util.mapper.MapperUtil;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.james.operation.UserOperation;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.SessionRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import com.mvg.sky.repository.entity.ProfileEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;
    private final DomainRepository domainRepository;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MapperUtil mapperUtil;
    private final UserOperation userOperation;

    @Override
    public LoginResponse authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        AccountDomainDto accountDomainDto = userPrincipal.accountDomainDto();
        String accessToken = sessionService.createAccessToken(accountDomainDto);
        String refreshToken = sessionService.createRefreshToken(accountDomainDto);

        sessionRepository.save(SessionEntity.builder()
           .token(refreshToken)
           .accountId(accountDomainDto.getAccountEntity().getId())
           .build()
        );

        log.info("user {} login successfully", accountDomainDto.getAccountEntity());
        return LoginResponse.builder()
            .account(accountDomainDto.getAccountEntity())
            .domain(accountDomainDto.getDomainEntity())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .build();
    }

    @Override
    public AccountEntity createAccount(String email, String password, RoleEnumeration[] roles) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        int at = email.indexOf('@');
        String domain = email.substring(at + 1);

        DomainEntity domainEntity = domainRepository.findByNameAndIsDeletedFalse(domain);
        if (domainEntity == null) {
            log.info("Domain of account do not exist!");
            throw new RuntimeException("Domain of account do not exist!");
        }

        // Create account on Apache James
        if(!userOperation.verifyExists(email)) {
            userOperation.addUser(email);
        }

        AccountEntity accountEntity = accountRepository.findByUsernameAndIsDeletedTrue(email);

        if(accountEntity != null) {
            accountEntity.setIsDeleted(false);
            accountEntity.setPassword(passwordEncoder.encode(password));
            accountEntity.setRoles(roles);
        }
        else {
            accountEntity = AccountEntity.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .domainId(domainEntity.getId())
                .build();
        }

        accountEntity = accountRepository.save(accountEntity);

        log.info("Save new account {}", accountEntity);
        return accountEntity;
    }

    @Override
    public AccountEntity updatePartialAccount(String accountId, AccountUpdateRequest accountUpdateRequest) {
        AccountEntity accountEntity = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new RuntimeException("Account do not exists"));

        mapperUtil.updatePartialAccountFromDto(accountUpdateRequest, accountEntity);
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        accountEntity = accountRepository.save(accountEntity);

        log.info("update account {}", accountEntity);
        return accountEntity;
    }

    @Override
    public Collection<AccountEntity> getAllAccounts(List<String> domainsIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> domainsUuids = domainsIds != null ? domainsIds.stream().map(UUID::fromString).toList() : null;
        Collection<AccountEntity> accountEntities = accountRepository.findAllAccounts(domainsUuids, pageable);

        log.info("find {} account entities", accountEntities == null ? 0 : accountEntities.size());
        return accountEntities;
    }

    @Override
    public Integer logoutAccount(String accountId, String refreshToken, Boolean all) {
        int num;

        if(all == null || !all) {
            num = sessionRepository.deleteByAccountIdAndTokenAndIsDeletedFalse(UUID.fromString(accountId), refreshToken);
            log.info("logout successfully, {} entities updated", num);
        }
        else {
            num = sessionRepository.deleteAllByAccountIdAndIsDeletedFalse(UUID.fromString(accountId));
            log.info("logout all devices successfully, {} entities updated", num);
        }

        return num;
    }

    @Override
    public Integer deleteAccountById(String accountId) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        AccountEntity accountEntity = accountRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(UUID.fromString(accountId));

        if(accountEntity == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        accountRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(accountId));

        String email = accountEntity.getUsername();

        if(userOperation.verifyExists(email)) {
            userOperation.deleteUser(email);
        }

        log.info("delete account successfully, {} records updated", 1);
        return 1;
    }

    @Override
    public AccountProfileCreationResponse createAccountProfile(AccountProfileCreationRequest accountProfileCreationRequest) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        AccountEntity accountEntity = createAccount(
            accountProfileCreationRequest.getAccount().getEmail(),
            accountProfileCreationRequest.getAccount().getPassword(),
            accountProfileCreationRequest.getAccount().getRoles()
        );

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setAccountId(accountEntity.getId());

        mapperUtil.updateFullProfileFromDto(accountProfileCreationRequest.getProfile(), profileEntity);

        AccountProfileCreationResponse accountProfileCreationResponse = new AccountProfileCreationResponse();
        accountProfileCreationResponse.setAccount(accountEntity);
        accountProfileCreationResponse.setProfile(profileEntity);

        log.info("create account with profile {}", accountProfileCreationResponse);
        return accountProfileCreationResponse;
    }
}
