package com.mvg.sky.account.service.account;

import com.mvg.sky.repository.entity.AccountEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    AccountEntity authenticate(String email, String password);
}
