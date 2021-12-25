package com.mvg.sky.mail.dto.request;

import com.mvg.sky.common.enumeration.EmailEnumeration;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MailStatusRequest {
    @NotNull
    private EmailEnumeration status;
}
