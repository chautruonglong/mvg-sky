package com.mvg.sky.repository.dto.query;

import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDomainDto {
    private AccountEntity accountEntity;
    private DomainEntity domainEntity;
}
