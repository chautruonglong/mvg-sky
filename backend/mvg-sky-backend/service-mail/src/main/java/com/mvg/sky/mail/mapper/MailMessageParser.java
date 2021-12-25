package com.mvg.sky.mail.mapper;

import com.google.common.net.UrlEscapers;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MailMessageParser extends MimeMessageParser {
    private final List<String> urlAttachments;
    private final String externalResources;
    private String htmlContent;

    public MailMessageParser(MimeMessage message, String externalResources) throws Exception {
        super(message);
        super.parse();

        this.externalResources = externalResources;
        this.urlAttachments = new ArrayList<>();
        this.htmlContent = super.getHtmlContent().replaceAll("\\r\\n|\\r|\\n|\\R", "");
    }

    public boolean isInlineImage() {
        if(hasHtmlContent() && getHtmlContent() != null) {
            Document document = Jsoup.parse(getHtmlContent());
            return document.select("img") != null;
        }
        return false;
    }

    public void replaceCidWithUrl() {
        if(isInlineImage()) {
            Document document = Jsoup.parse(getHtmlContent());
            Elements elements = document.select("img");
            elements.forEach(element -> {
                try {
                    String cid = element.attr("src");
                    DataSource dataSource = findAttachmentByCid(cid.substring("cid:".length()));
                    String path = getMimeMessage().getMessageID() + "/" + dataSource.getName();
                    String escape = UrlEscapers.urlFragmentEscaper().escape("http://api.mvg-sky.com/api/mails-resources/attachment/" + path);
                    element.attr("src", escape);
                }
                catch(MessagingException e) {
                    e.printStackTrace();
                }
            });
            htmlContent = document.html().replaceAll("\\r\\n|\\r|\\n|\\R", "");
        }
    }

    public void serveAttachments() throws MessagingException {
        if(hasAttachments()) {
            List<DataSource> attachments = getAttachmentList();
            String messageId = getMimeMessage().getMessageID();

            attachments.forEach(attachment -> {
                try {
                    String fileName = messageId + "/" + attachment.getName();;
                    String path = externalResources.substring("file:".length()) + "/mails-resources/attachment/";
                    Files.createDirectories(Paths.get(path + messageId));
                    DataHandler dataHandler = new DataHandler(attachment);

                    dataHandler.writeTo(new FileOutputStream(path + fileName));
                    urlAttachments.add(fileName);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public List<String> getUrlAttachments() {
        return urlAttachments;
    }

    @Override
    public String getHtmlContent() {
        return htmlContent;
    }

    public String getMessageId() throws MessagingException {
        return getMimeMessage().getMessageID();
    }

    public Date getSendDate() throws MessagingException {
        return getMimeMessage().getSentDate();
    }
}
