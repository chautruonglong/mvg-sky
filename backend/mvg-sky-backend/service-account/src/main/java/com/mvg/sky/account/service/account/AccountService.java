package com.mvg.sky.account.service.account;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Object authenticate(String email);
}
