package com.mvg.sky.mail.service.mail;

import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.repository.entity.MailEntity;
import java.io.IOException;
import javax.mail.MessagingException;

public interface MailService {
    String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException;
}
