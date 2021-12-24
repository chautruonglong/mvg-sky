package com.mvg.sky.mail.heper;

import com.mvg.sky.mail.dto.request.MailSendingRequest;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public record MimeMessageBuilder(JavaMailSender javaMailSender) {
    public MimeMessage buildMimeMessage(String from, MailSendingRequest mailSendingRequest) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(mailSendingRequest.getTo().toArray(String[]::new));
        mimeMessageHelper.setSubject(mailSendingRequest.getSubject());
        mimeMessageHelper.setText(mailSendingRequest.getBody(), true);

        if(mailSendingRequest.getCc() != null) {
            mimeMessageHelper.setCc(mailSendingRequest.getCc().toArray(String[]::new));
        }

        if(mailSendingRequest.getBcc() != null) {
            mimeMessageHelper.setCc(mailSendingRequest.getBcc().toArray(String[]::new));
        }

        if(mailSendingRequest.getAttachments() != null) {
            for(MultipartFile attachment : mailSendingRequest.getAttachments()) {
                String fileName = attachment.getOriginalFilename() != null ? attachment.getOriginalFilename() : "unnamed";
                mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(attachment.getInputStream())));
            }
        }

        return mimeMessage;
    }
}
