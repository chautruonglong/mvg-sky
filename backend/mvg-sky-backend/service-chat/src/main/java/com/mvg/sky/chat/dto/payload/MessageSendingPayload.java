package com.mvg.sky.chat.dto.payload;

import com.mvg.sky.common.enumeration.MessageEnumeration;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
