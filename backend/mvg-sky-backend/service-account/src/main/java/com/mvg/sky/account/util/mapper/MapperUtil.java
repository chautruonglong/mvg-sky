package com.mvg.sky.account.util.mapper;

import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.ProfileEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MapperUtil {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartialAccountFromDto(AccountUpdateRequest accountUpdateRequest, @MappingTarget AccountEntity accountEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void createProfileFromDto(ProfileCreationRequest profileCreationRequest, @MappingTarget ProfileEntity profileEntity);

    void updateFullProfileFromDto(ProfileModifyRequest profileModifyRequest, @MappingTarget ProfileEntity profileEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartialProfileFromDto(ProfileModifyRequest profileModifyRequest, @MappingTarget ProfileEntity profileEntity);
}
