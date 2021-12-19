package com.mvg.sky.chat.dto.request;

import com.mvg.sky.common.enumeration.MessageEnumeration;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MediaMessageRequest {
    private String threadId;

    @NotNull
    @NotBlank
    private String accountId;

    @NotNull
    private MultipartFile content;

    @NotNull
    private MessageEnumeration type;

    @NotNull
    private Integer delay = 0;
}
