package com.mvg.sky.mail.service.mail;

import com.mvg.sky.mail.component.MimeMessageBuilder;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.task.MailSendingTask;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.FolderRepository;
import com.mvg.sky.repository.MailRepository;
import com.mvg.sky.repository.entity.MailEntity;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MimeMessageBuilder mimeMessageBuilder;

    public String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException {
        MimeMessage mimeMessage = mimeMessageBuilder.buildMimeMessage(mailSendingRequest);

        if(mailSendingRequest.getEnableThread()) {
            MailSendingTask mailSendingTask = new MailSendingTask(javaMailSender, mimeMessage);
            mailSendingTask.start();

            return "Sending email...";
        }
        else {
            javaMailSender.send(mimeMessage);

            return "Sent email";
        }
    }
}
