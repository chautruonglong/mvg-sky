package com.mvg.sky.mail.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.net.UrlEscapers;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailResponse {
    private Long mailId;

    private Long mailboxId;

    private Boolean isRecent;

    private Boolean isSeen;

    private Boolean isAnswered;

    private String messageId;

    private String from;

    private List<@Email String> to;

    private List<@Email String> cc;

    private String subject;

    private String body;

    private List<String> attachments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date sendDate;

    public List<String> getAttachments() {
        if(attachments != null) {
            return attachments.stream()
                .map(attachment -> UrlEscapers.urlFragmentEscaper().escape("/api/mails-resources/attachment/" + attachment))
                .collect(Collectors.toList());
        }
        return null;
    }
}
