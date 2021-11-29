package com.mvg.sky.account.dto.response;

import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.ProfileEntity;
import lombok.Data;

@Data
public class AccountProfileCreationResponse {
    private AccountEntity account;
    private ProfileEntity profile;
}
