package com.mvg.sky.account.service.account;

import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.account.dto.response.LoginResponse;
import com.mvg.sky.account.security.UserPrincipal;
import com.mvg.sky.account.service.session.SessionService;
import com.mvg.sky.account.util.mapper.MapperUtil;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.SessionRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final SessionRepository sessionRepository;
    @NonNull
    private final DomainRepository domainRepository;
    @NonNull
    private final SessionService sessionService;
    @NonNull
    private final AuthenticationManager authenticationManager;
    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final MapperUtil mapperUtil;

    @Value("${com.mvg.sky.service-account.secret}")
    private String secretKey;

    @Value("${com.mvg.sky.service-account.access.expiration}")
    private Long accessTokenExpiration;

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
            .accountEntity(accountDomainDto.getAccountEntity())
            .domainEntity(accountDomainDto.getDomainEntity())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .build();
    }

    @Override
    public AccountEntity createAccount(String email, String password, RoleEnumeration[] roles) {
        int at = email.indexOf('@');
        String username = email.substring(0, at);
        String domain = email.substring(at + 1);

        DomainEntity domainEntity = domainRepository.findByNameAndIsDeletedFalse(domain);
        if (domainEntity == null) {
            log.info("Domain of account do not exist!");
            throw new RuntimeException("Domain of account do not exist!");
        }

        AccountEntity accountEntity = AccountEntity.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles(roles)
            .domainId(domainEntity.getId())
            .build();

        AccountEntity savedAccountEntity = accountRepository.save(accountEntity);

        log.info("Save new account {}", savedAccountEntity);
        return savedAccountEntity;
    }

    @Override
    public AccountEntity updatePartialAccount(String accountId, AccountUpdateRequest accountUpdateRequest) {
        AccountEntity accountEntity = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new RuntimeException("Account do not exists"));
        mapperUtil.updateAccountFromDto(accountUpdateRequest, accountEntity);
        AccountEntity savedAccountEntity = accountRepository.save(accountEntity);

        log.info("update account {}", savedAccountEntity);
        return savedAccountEntity;
    }

    @Override
    public Collection<AccountEntity> getAllAccounts(List<String> domainsIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<AccountEntity> accountEntities;

        if(domainsIds == null) {
            accountEntities = accountRepository.findAllByIsDeletedFalse(pageable);
        }
        else {
            accountEntities = accountRepository.findAllByDomainIdInAndIsDeletedFalse(
                domainsIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                pageable
            );
        }

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
    public Integer deleteAccountById(String accountId) {
        int num = accountRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(accountId));

        log.info("delete account successfully, {} records updated", num);
        return num;
    }
}
