package com.mvg.sky.account.util.mapper;

import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.repository.entity.AccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MapperUtil {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountFromDto(AccountUpdateRequest accountUpdateRequest, @MappingTarget AccountEntity accountEntity);
}
