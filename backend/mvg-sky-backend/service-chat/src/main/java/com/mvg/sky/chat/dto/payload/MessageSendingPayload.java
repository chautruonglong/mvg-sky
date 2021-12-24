package com.mvg.sky.chat.dto.payload;

import com.mvg.sky.common.enumeration.MessageEnumeration;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageSendingPayload {
    private String threadId;

    @NotNull
    @NotBlank
    private String accountId;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    private MessageEnumeration type;

    @NotNull
    private Integer delay = 0;
}
