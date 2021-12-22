package com.mvg.sky.mail.service.mail;

import com.mvg.sky.mail.dto.request.MailSendingRequest;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public interface MailService {
    String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException;
    void fetchMails(String accountId, List<String> sorts, Integer offset, Integer limit) throws MessagingException;
}
