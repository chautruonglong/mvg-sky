package com.mvg.sky.mail.service.mail;

import com.mvg.sky.mail.component.MimeMessageBuilder;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.task.MailSendingTask;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
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
    private final AccountRepository accountRepository;

    @Override
    public String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(mailSendingRequest.getAccountId()));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String from = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();
        MimeMessage mimeMessage = mimeMessageBuilder.buildMimeMessage(from, mailSendingRequest);

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

    @Override
    public void fetchMails(String accountId, List<String> sorts, Integer offset, Integer limit) throws MessagingException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(accountId));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String from = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imap");

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("imap");
        store.connect("imap.mvg-sky.com", "longchau@mvg-sky.com", "Ctlbi@0775516337");
    }
}
