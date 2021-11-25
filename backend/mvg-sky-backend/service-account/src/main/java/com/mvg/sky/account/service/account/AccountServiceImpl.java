package com.mvg.sky.account.service.account;

import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public AccountEntity authenticate(String email, String password) {
        int at = email.indexOf('@');
        String username = email.substring(0, at);
        String domain = email.substring(at + 1);

        AccountEntity accountEntity = accountRepository.findAccountByEmail(username, domain);

        if(accountEntity == null || !accountEntity.getPassword().equals(password)) {
            throw new RuntimeException("User do not exists or unactivated");
        }

        return accountEntity;
    }
}
