package com.mvg.sky.account.service.account;

import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public Object authenticate(String email) {
        int at = email.indexOf('@');
        String username = email.substring(0, at);
        String domain = email.substring(at + 1);

        AccountEntity accountEntity = accountRepository.findAccountByEmail(email);
        log.info(accountEntity.toString());
        return accountEntity;
    }
}
