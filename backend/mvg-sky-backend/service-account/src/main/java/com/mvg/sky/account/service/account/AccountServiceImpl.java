package com.mvg.sky.account.service.account;

import com.mvg.sky.account.dto.response.LoginResponse;
import com.mvg.sky.account.security.UserPrincipal;
import com.mvg.sky.account.service.session.SessionService;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.SessionRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public LoginResponse authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        AccountEntity accountEntity = userPrincipal.accountEntity();
        String accessToken = sessionService.createAccessToken(accountEntity);
        String refreshToken = sessionService.createRefreshToken(accountEntity);

        sessionRepository.save(SessionEntity.builder()
           .token(refreshToken)
           .accountId(accountEntity.getId())
           .build()
        );

        log.info("user {} login successfully", accountEntity);
        return LoginResponse.builder()
            .accountEntity(accountEntity)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Override
    public void logoutAccount() {

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
}
