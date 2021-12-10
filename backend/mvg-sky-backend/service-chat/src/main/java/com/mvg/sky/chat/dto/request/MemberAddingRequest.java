package com.mvg.sky.chat.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberAddingRequest {
    @NotNull
    @NotBlank
    private String accountId;

    @NotNull
    @NotBlank
    private String roomId;
}
