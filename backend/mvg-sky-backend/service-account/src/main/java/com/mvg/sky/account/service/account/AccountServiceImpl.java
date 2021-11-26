package com.mvg.sky.account.service.account;

import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public SessionEntity authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return null;
    }

    @Override
    public AccountEntity createAccount(AccountEntity accountEntity) {
        return null;
    }
}
