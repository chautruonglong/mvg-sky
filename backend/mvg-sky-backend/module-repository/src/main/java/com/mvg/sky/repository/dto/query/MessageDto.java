package com.mvg.sky.repository.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.entity.MessageEntity;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class MessageDto {
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    private Boolean isDeleted;

    private UUID accountId;

    private UUID roomId;

    private UUID threadId;

    private String content;

    private MessageEnumeration type;

    private Boolean isInSchedule;

    private MessageEntity repliedMessage;

    public MessageDto(MessageEntity messageEntity, MessageEntity repliedMessage) {
        this.id = messageEntity.getId();
        this.createdAt = messageEntity.getCreatedAt();
        this.updatedAt = messageEntity.getUpdatedAt();
        this.isDeleted = messageEntity.getIsDeleted();
        this.accountId = messageEntity.getAccountId();
        this.roomId = messageEntity.getRoomId();
        this.threadId = messageEntity.getThreadId();
        this.content = messageEntity.getContent();
        this.type = messageEntity.getType();
        this.isInSchedule = messageEntity.getIsInSchedule();
        this.repliedMessage = repliedMessage;
    }
}
