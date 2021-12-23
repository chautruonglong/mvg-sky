package com.mvg.sky.james.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "james_mail", indexes = {@Index(name = "i_jms_mil_mail_is_seen", columnList = "mail_is_seen"), @Index(name = "i_jms_mil_mail_is_deleted", columnList = "mail_is_deleted"), @Index(name = "i_jms_mil_mail_is_recent", columnList = "mail_is_recent"), @Index(name = "i_jms_mil_mail_modseq", columnList = "mail_modseq")})
public class JamesMail {
    @EmbeddedId
    private JamesMailId id;

    @Column(name = "mail_is_answered", nullable = false)
    private Boolean mailIsAnswered = false;

    @Column(name = "mail_body_start_octet", nullable = false)
    private Integer mailBodyStartOctet;

    @Column(name = "mail_content_octets_count", nullable = false)
    private Long mailContentOctetsCount;

    @Column(name = "mail_is_deleted", nullable = false)
    private Boolean mailIsDeleted = false;

    @Column(name = "mail_is_draft", nullable = false)
    private Boolean mailIsDraft = false;

    @Column(name = "mail_is_flagged", nullable = false)
    private Boolean mailIsFlagged = false;

    @Column(name = "mail_date")
    private Instant mailDate;

    @Column(name = "mail_mime_type", length = 200)
    private String mailMimeType;

    @Column(name = "mail_modseq")
    private Long mailModseq;

    @Column(name = "mail_is_recent", nullable = false)
    private Boolean mailIsRecent = false;

    @Column(name = "mail_is_seen", nullable = false)
    private Boolean mailIsSeen = false;

    @Column(name = "mail_mime_subtype", length = 200)
    private String mailMimeSubtype;

    @Column(name = "mail_textual_line_count")
    private Long mailTextualLineCount;

    @Column(name = "mail_bytes", nullable = false)
    private byte[] mailBytes;

    @Column(name = "header_bytes", nullable = false)
    private byte[] headerBytes;

    public byte[] getHeaderBytes() {return headerBytes;}

    public void setHeaderBytes(byte[] headerBytes) {this.headerBytes = headerBytes;}

    public byte[] getMailBytes() {return mailBytes;}

    public void setMailBytes(byte[] mailBytes) {this.mailBytes = mailBytes;}

    public Long getMailTextualLineCount() {return mailTextualLineCount;}

    public void setMailTextualLineCount(Long mailTextualLineCount) {this.mailTextualLineCount = mailTextualLineCount;}

    public String getMailMimeSubtype() {return mailMimeSubtype;}

    public void setMailMimeSubtype(String mailMimeSubtype) {this.mailMimeSubtype = mailMimeSubtype;}

    public Boolean getMailIsSeen() {return mailIsSeen;}

    public void setMailIsSeen(Boolean mailIsSeen) {this.mailIsSeen = mailIsSeen;}

    public Boolean getMailIsRecent() {return mailIsRecent;}

    public void setMailIsRecent(Boolean mailIsRecent) {this.mailIsRecent = mailIsRecent;}

    public Long getMailModseq() {return mailModseq;}

    public void setMailModseq(Long mailModseq) {this.mailModseq = mailModseq;}

    public String getMailMimeType() {return mailMimeType;}

    public void setMailMimeType(String mailMimeType) {this.mailMimeType = mailMimeType;}

    public Instant getMailDate() {return mailDate;}

    public void setMailDate(Instant mailDate) {this.mailDate = mailDate;}

    public Boolean getMailIsFlagged() {return mailIsFlagged;}

    public void setMailIsFlagged(Boolean mailIsFlagged) {this.mailIsFlagged = mailIsFlagged;}

    public Boolean getMailIsDraft() {return mailIsDraft;}

    public void setMailIsDraft(Boolean mailIsDraft) {this.mailIsDraft = mailIsDraft;}

    public Boolean getMailIsDeleted() {return mailIsDeleted;}

    public void setMailIsDeleted(Boolean mailIsDeleted) {this.mailIsDeleted = mailIsDeleted;}

    public Long getMailContentOctetsCount() {return mailContentOctetsCount;}

    public void setMailContentOctetsCount(Long mailContentOctetsCount) {this.mailContentOctetsCount = mailContentOctetsCount;}

    public Integer getMailBodyStartOctet() {return mailBodyStartOctet;}

    public void setMailBodyStartOctet(Integer mailBodyStartOctet) {this.mailBodyStartOctet = mailBodyStartOctet;}

    public Boolean getMailIsAnswered() {return mailIsAnswered;}

    public void setMailIsAnswered(Boolean mailIsAnswered) {this.mailIsAnswered = mailIsAnswered;}

    public JamesMailId getId() {return id;}

    public void setId(JamesMailId id) {this.id = id;}
}
