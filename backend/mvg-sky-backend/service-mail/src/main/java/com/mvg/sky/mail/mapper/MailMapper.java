package com.mvg.sky.mail.mapper;

import com.mvg.sky.james.entity.JamesMail;
import com.mvg.sky.mail.dto.response.MailResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailMapper {
    @Value("${com.mvg.sky.service-mail.external-resource}")
    private String externalResources;

    public List<MailResponse> fromJamesMails(List<JamesMail> jamesMails) {
        List<MailResponse> mailResponses = new ArrayList<>();

        jamesMails.stream()
            .parallel()
            .forEachOrdered(jamesMail -> {
                try {
                    byte[] mailBytes = ArrayUtils.addAll(jamesMail.getHeaderBytes(), jamesMail.getMailBytes());
                    InputStream inputStream = new ByteArrayInputStream(mailBytes);
                    Session session = Session.getDefaultInstance(new Properties(), null);
                    MimeMessage mimeMessage = new MimeMessage(session, inputStream);
                    MailMessageParser mailMessageParser = new MailMessageParser(mimeMessage, externalResources);

                    mailMessageParser.serveAttachments();
                    mailMessageParser.replaceCidWithUrl();

                    mailResponses.add(
                        MailResponse.builder()
                            .mailId(jamesMail.getId().getMailUid())
                            .mailboxId(jamesMail.getId().getMailboxId())
                            .isRecent(jamesMail.getMailIsRecent())
                            .isSeen(jamesMail.getMailIsSeen())
                            .isAnswered(jamesMail.getMailIsAnswered())
                            .messageId(mailMessageParser.getMessageId())
                            .from(mailMessageParser.getFrom())
                            .to(mailMessageParser.getTo().stream().map(Address::toString).collect(Collectors.toList()))
                            .cc(mailMessageParser.getCc().stream().map(Address::toString).collect(Collectors.toList()))
                            .subject(mailMessageParser.getSubject())
                            .body(mailMessageParser.hasHtmlContent() ? mailMessageParser.getHtmlContent() : mailMessageParser.getPlainContent())
                            .attachments(mailMessageParser.getUrlAttachments())
                            .sendDate(mailMessageParser.getSendDate())
                            .build());
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            });
        return mailResponses;
    }

    public MailResponse fromJamesMail(JamesMail jamesMail) throws Exception {
        byte[] mailBytes = ArrayUtils.addAll(jamesMail.getHeaderBytes(), jamesMail.getMailBytes());
        InputStream inputStream = new ByteArrayInputStream(mailBytes);
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(session, inputStream);
        MailMessageParser mailMessageParser = new MailMessageParser(mimeMessage, externalResources);

        mailMessageParser.serveAttachments();
        mailMessageParser.replaceCidWithUrl();

        return MailResponse.builder()
            .mailId(jamesMail.getId().getMailUid())
            .mailboxId(jamesMail.getId().getMailboxId())
            .isRecent(jamesMail.getMailIsRecent())
            .isSeen(jamesMail.getMailIsSeen())
            .isAnswered(jamesMail.getMailIsAnswered())
            .messageId(mailMessageParser.getMessageId())
            .from(mailMessageParser.getFrom())
            .to(mailMessageParser.getTo().stream().map(Address::toString).collect(Collectors.toList()))
            .cc(mailMessageParser.getCc().stream().map(Address::toString).collect(Collectors.toList()))
            .subject(mailMessageParser.getSubject())
            .body(mailMessageParser.hasHtmlContent() ? mailMessageParser.getHtmlContent() : mailMessageParser.getPlainContent())
            .attachments(mailMessageParser.getUrlAttachments())
            .sendDate(mailMessageParser.getSendDate())
            .build();
    }
}
