package com.mvg.sky.account.dto.jwt;

import com.mvg.sky.common.enumeration.RoleEnumeration;
import lombok.Data;

@Data
public class JwtAccessTokenBody {
    private String iss;
    private Long iat;
    private Long exp;
    private String jti;
    private String username;
    private String domain;
    private RoleEnumeration[] roles;
}
