package com.mvg.sky.mail.service.mailbox;

import com.mvg.sky.mail.dto.request.MailboxCreationRequest;
import java.io.IOException;
import java.util.Collection;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

public interface MailboxService {
    Collection<?> getMailboxes(String accountId) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException;
    Collection<?> createMailbox(MailboxCreationRequest mailboxCreationRequest) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException;
    Collection<?> deleteMailbox(String accountId, String mailbox) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException;
}
