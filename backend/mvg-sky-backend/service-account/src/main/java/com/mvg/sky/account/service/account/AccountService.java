package com.mvg.sky.account.service.account;

import com.mvg.sky.account.dto.response.LoginResponse;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.entity.AccountEntity;
import java.util.List;

public interface AccountService  {
    LoginResponse authenticate(String email, String password);

    void logoutAccount();

    AccountEntity createAccount(String email, String password, RoleEnumeration[] roles);
}
