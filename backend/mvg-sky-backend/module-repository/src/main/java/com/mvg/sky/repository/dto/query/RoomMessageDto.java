package com.mvg.sky.repository.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvg.sky.common.enumeration.RoomEnumeration;
import com.mvg.sky.repository.entity.MessageEntity;
import com.mvg.sky.repository.entity.RoomEntity;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class RoomMessageDto {
    private UUID id;

    private String name;

    private String description;

    private String avatar;

    private RoomEnumeration type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    private Boolean isDeleted;

    private MessageEntity latestMessage;

    public RoomMessageDto(RoomEntity roomEntity, MessageEntity messageEntity) {
        this.id = roomEntity.getId();
        this.name = roomEntity.getName();
        this.description = roomEntity.getDescription();
        this.avatar = roomEntity.getAvatar();
        this.type = roomEntity.getType();
        this.createdAt = roomEntity.getCreatedAt();
        this.updatedAt = roomEntity.getUpdatedAt();
        this.isDeleted = roomEntity.getIsDeleted();
        this.latestMessage = messageEntity;
    }
}
