package com.mvg.sky.account.dto.jwt;

import lombok.Data;

@Data
public class JwtRefreshTokenBody {
    private String iss;
    private Long iat;
    private Long exp;
    private String jti;
    private String username;
    private String domain;
}
