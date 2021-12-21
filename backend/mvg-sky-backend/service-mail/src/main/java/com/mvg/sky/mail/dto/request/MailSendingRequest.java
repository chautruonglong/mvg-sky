package com.mvg.sky.mail.dto.request;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MailSendingRequest {
    @NotNull
    @NotBlank
    private String accountId;

    @NotNull
    private List<@Email String> to;

    private List<@Email String> cc;

    private List<@Email String> bcc;

    private String subject;

    private String body;

    private List<MultipartFile> attachments;

    @NotNull
    private Boolean enableThread = false;
}
