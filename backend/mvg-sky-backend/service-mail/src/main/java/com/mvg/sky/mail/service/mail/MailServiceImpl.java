package com.mvg.sky.mail.service.mail;

import com.mvg.sky.common.enumeration.EmailEnumeration;
import com.mvg.sky.james.entity.JamesMail;
import com.mvg.sky.james.repository.JamesMailRepository;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.dto.request.MailboxCreationRequest;
import com.mvg.sky.mail.dto.request.MailsDeletingRequest;
import com.mvg.sky.mail.dto.response.MailResponse;
import com.mvg.sky.mail.heper.MimeMessageBuilder;
import com.mvg.sky.mail.mapper.MailMapper;
import com.mvg.sky.mail.service.mailbox.MailboxService;
import com.mvg.sky.mail.task.MailSendingTask;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MimeMessageBuilder mimeMessageBuilder;
    private final AccountRepository accountRepository;
    private final JamesMailRepository jamesMailRepository;
    private final MailMapper mailMapper;
    private final MailboxService mailboxService;

    @Override
    public String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException, ReflectionException, InstanceNotFoundException, MBeanException {
        AccountEntity accountEntity = accountRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(UUID.fromString(mailSendingRequest.getAccountId()));

        if(accountEntity == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        try {
            MailboxCreationRequest mailboxCreationRequest = new MailboxCreationRequest();
            mailboxCreationRequest.setAccountId(mailSendingRequest.getAccountId());
            mailboxCreationRequest.setName("Sent");
            mailboxService.createMailbox(mailboxCreationRequest);
        }
        catch(RuntimeException exception) {
            log.warn("mailbox is exists {}", exception.getMessage());
        }

        MimeMessage mimeMessage = mimeMessageBuilder.buildMimeMessage(accountEntity.getUsername(), mailSendingRequest);

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
    public Collection<MailResponse> fetchMails(String accountId, String mailbox, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        AccountEntity accountEntity = accountRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(UUID.fromString(accountId));

        if(accountEntity == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        List<JamesMail> jamesMails = jamesMailRepository.fetchMails(accountEntity.getUsername(), mailbox, pageable);

        log.info("find {} emails", jamesMails.size());
        return mailMapper.fromJamesMails(jamesMails);
    }

    @Override
    public MailResponse fetchMailById(Long mailId) throws Exception {
        JamesMail jamesMail = jamesMailRepository.fetchMailById(mailId);

        log.info("fetch a email {}", jamesMail);
        return mailMapper.fromJamesMail(jamesMail);
    }

    @Override
    public Integer changeMailStatus(Long mailId, EmailEnumeration status) {
        int num = jamesMailRepository.updateStatus(mailId, status.name());

        log.info("update status successfully, {} records changed", num);
        return num;
    }

    @Override
    public Integer deleteMail(Long mailId) {
        int num = jamesMailRepository.deleteJamesMailByMailId(mailId);

        log.info("delete email successfully, {} records changed", num);
        return num;
    }

    @Override
    public Integer deleteMails(MailsDeletingRequest mailsDeletingRequest) {
        int num = mailsDeletingRequest.getMailIds() == null ? 0 :
                  jamesMailRepository.deleteBatchJamesMailsByMailIds(mailsDeletingRequest.getMailIds());

        log.info("delete email successfully, {} records changed", num);
        return num;
    }
}
