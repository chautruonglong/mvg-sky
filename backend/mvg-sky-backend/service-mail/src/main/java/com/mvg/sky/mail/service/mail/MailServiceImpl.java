package com.mvg.sky.mail.service.mail;

import com.mvg.sky.common.enumeration.EmailEnumeration;
import com.mvg.sky.james.entity.JamesMail;
import com.mvg.sky.james.repository.JamesMailRepository;
import com.mvg.sky.mail.component.MimeMessageBuilder;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.dto.request.MailsDeletingRequest;
import com.mvg.sky.mail.dto.response.MailResponse;
import com.mvg.sky.mail.mapper.MailMapper;
import com.mvg.sky.mail.task.MailSendingTask;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    public Collection<MailResponse> fetchMails(String accountId, String mailbox, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(accountId));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String email = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();

        List<JamesMail> jamesMails = jamesMailRepository.fetchMails(email, mailbox, pageable);

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
