package com.mvg.sky.account.dto.response;

import com.mvg.sky.repository.entity.AccountEntity;
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
    private AccountEntity accountEntity;
    private String accessToken;
    private String refreshToken;
}
