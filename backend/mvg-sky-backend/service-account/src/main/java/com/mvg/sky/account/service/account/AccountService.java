package com.mvg.sky.account.service.account;

import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.SessionEntity;

public interface AccountService  {
    SessionEntity authenticate(String email, String password);
    AccountEntity createAccount(AccountEntity accountEntity);
}
