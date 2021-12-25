package com.mvg.sky.mail.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MailboxCreationRequest {
    @NotNull
    @NotBlank
    private String accountId;

    private String namespace;

    @NotNull
    @NotBlank
    private String name;
}
