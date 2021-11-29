package com.mvg.sky.account.dto.response;

import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private AccountEntity account;
    private DomainEntity domain;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
