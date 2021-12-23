package com.mvg.sky.mail.service.mailbox;

import com.mvg.sky.james.operation.MailboxOperation;
import com.mvg.sky.mail.dto.request.MailboxCreationRequest;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailboxServiceImpl implements MailboxService {
    private final AccountRepository accountRepository;
    private final MailboxOperation mailboxOperation;

    @Override
    public Collection<?> getMailboxes(String accountId) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(accountId));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String email = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();
        return mailboxOperation.listMailboxes(email);
    }

    @Override
    public Collection<?> createMailbox(MailboxCreationRequest mailboxCreationRequest) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(mailboxCreationRequest.getAccountId()));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String email = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();
        String mailboxName = mailboxCreationRequest.getName();

        if(mailboxCreationRequest.getNamespace() != null) {
            mailboxName = mailboxCreationRequest.getNamespace() + "." + mailboxName;
        }

        mailboxOperation.createMailbox("#private", email, mailboxName);
        return mailboxOperation.listMailboxes(email);
    }

    @Override
    public Collection<?> deleteMailbox(String accountId, String mailbox) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        AccountDomainDto accountDomainDto = accountRepository.findAccountById(UUID.fromString(accountId));

        if(accountDomainDto == null) {
            log.error("Not found account in database");
            throw new RuntimeException("Not found account in database");
        }

        String email = accountDomainDto.getAccountEntity().getUsername() + "@" + accountDomainDto.getDomainEntity().getName();

        if(mailbox == null) {
            mailboxOperation.deleteMailboxes(email);
        }
        else {
            mailboxOperation.deleteMailbox("#private", email, mailbox);
        }

        return mailboxOperation.listMailboxes(email);
    }

}
