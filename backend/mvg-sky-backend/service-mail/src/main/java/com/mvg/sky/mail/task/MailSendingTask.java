package com.mvg.sky.mail.task;

import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@AllArgsConstructor
public class MailSendingTask extends Thread {
    private final JavaMailSender javaMailSender;
    private final MimeMessage mimeMessage;

    @Override
    public void run() {
        try {
            javaMailSender.send(mimeMessage);
        }
        catch(MailException exception) {
            log.error("Can not send email: {}", exception.getMessage());
        }
    }
}
