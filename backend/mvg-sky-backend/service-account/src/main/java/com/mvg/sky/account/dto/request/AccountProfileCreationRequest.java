package com.mvg.sky.account.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountProfileCreationRequest {
    @NotNull
    private AccountCreationRequest account;

    @NotNull
    private ProfileModifyRequest profile;
}
