package com.mvg.sky.account.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DomainCreationRequest {
    @Pattern(
        regexp = "(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9]",
        message = "domain do not match to the format"
    )
    @NotBlank
    @NotNull
    private String name;
}
