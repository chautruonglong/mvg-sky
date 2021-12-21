package com.mvg.sky.mail.component;

import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class MimeMessageBuilder {
    @NonNull
    private final JavaMailSender javaMailSender;

    @NonNull
    private final AccountRepository accountRepository;

    @Value("${com.mvg.sky.service-mail.external-resource}")
    private String externalResources;

    public MimeMessage buildMimeMessage(MailSendingRequest mailSendingRequest) throws MessagingException, IOException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(mailSendingRequest.getAccountId()));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String from = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();
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

    public void writeEmlToDisk() throws IOException {
        String path = externalResources.substring("file:".length()) + "/mails-resources/eml/";
        Files.createDirectories(Paths.get(path));
    }
}
