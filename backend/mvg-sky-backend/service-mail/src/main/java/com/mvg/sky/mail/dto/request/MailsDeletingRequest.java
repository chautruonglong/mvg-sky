package com.mvg.sky.mail.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class MailsDeletingRequest {
    private List<Long> mailIds;
}
