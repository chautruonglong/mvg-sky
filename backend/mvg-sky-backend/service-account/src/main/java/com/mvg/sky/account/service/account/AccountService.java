package com.mvg.sky.account.service.account;

import com.mvg.sky.account.dto.request.AccountProfileCreationRequest;
import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.account.dto.response.AccountProfileCreationResponse;
import com.mvg.sky.account.dto.response.LoginResponse;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.entity.AccountEntity;
import java.util.Collection;
import java.util.List;

public interface AccountService  {
    LoginResponse authenticate(String email, String password);

    AccountEntity createAccount(String email, String password, RoleEnumeration[] roles);

    AccountEntity updatePartialAccount(String accountId, AccountUpdateRequest accountUpdateRequest);

    Collection<AccountEntity> getAllAccounts(List<String> domainsIds,
                                             List<String> sorts,
                                             Integer offset,
                                             Integer limit);

    Integer logoutAccount(String accountId, String refreshToken, Boolean all);

    Integer deleteAccountById(String accountId);

    AccountProfileCreationResponse createAccountProfile(AccountProfileCreationRequest accountProfileCreationRequest);
}
