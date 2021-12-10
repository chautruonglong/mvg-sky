package com.mvg.sky.chat.util.mapper;

import com.mvg.sky.chat.dto.request.RoomUpdateRequest;
import com.mvg.sky.repository.entity.RoomEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MapperUtil {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartialRoomFromDto(RoomUpdateRequest roomUpdateRequest, @MappingTarget RoomEntity roomEntity);

    void updateRoomFromDto(RoomUpdateRequest roomUpdateRequest, @MappingTarget RoomEntity roomEntity);
}
