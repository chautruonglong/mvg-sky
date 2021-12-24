package com.mvg.sky.mail.task;

import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class MailSendingTask extends Thread {
    private final JavaMailSender javaMailSender;
    private final MimeMessage mimeMessage;

    public MailSendingTask(JavaMailSender javaMailSender, MimeMessage mimeMessage) {
        this.javaMailSender = javaMailSender;
        this.mimeMessage = mimeMessage;
    }

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
