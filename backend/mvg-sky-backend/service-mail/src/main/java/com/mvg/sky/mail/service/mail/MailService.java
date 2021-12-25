package com.mvg.sky.mail.service.mail;

import com.mvg.sky.common.enumeration.EmailEnumeration;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.dto.request.MailsDeletingRequest;
import com.mvg.sky.mail.dto.response.MailResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.mail.MessagingException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

public interface MailService {
    String sendMail(MailSendingRequest mailSendingRequest) throws MessagingException, IOException, ReflectionException, InstanceNotFoundException, MBeanException;

    Collection<MailResponse> fetchMails(String accountId, String mailbox, List<String> sorts, Integer offset, Integer limit) throws MessagingException, IOException;

    MailResponse fetchMailById(Long mailId) throws Exception;

    Integer changeMailStatus(Long mailId, EmailEnumeration status);

    Integer deleteMail(Long mailId);

    Integer deleteMails(MailsDeletingRequest mailsDeletingRequest);
}
