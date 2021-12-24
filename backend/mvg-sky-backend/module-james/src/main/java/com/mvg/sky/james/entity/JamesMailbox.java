package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_mailbox")
public class JamesMailbox {
    @Id
    @Column(name = "mailbox_id", nullable = false)
    private Long id;

    @Column(name = "mailbox_highest_modseq")
    private Long mailboxHighestModseq;

    @Column(name = "mailbox_last_uid")
    private Long mailboxLastUid;

    @Column(name = "mailbox_name", nullable = false, length = 200)
    private String mailboxName;

    @Column(name = "mailbox_namespace", nullable = false, length = 200)
    private String mailboxNamespace;

    @Column(name = "mailbox_uid_validity", nullable = false)
    private Long mailboxUidValidity;

    @Column(name = "user_name", length = 200)
    private String userName;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public Long getMailboxUidValidity() {return mailboxUidValidity;}

    public void setMailboxUidValidity(Long mailboxUidValidity) {this.mailboxUidValidity = mailboxUidValidity;}

    public String getMailboxNamespace() {return mailboxNamespace;}

    public void setMailboxNamespace(String mailboxNamespace) {this.mailboxNamespace = mailboxNamespace;}

    public String getMailboxName() {return mailboxName;}

    public void setMailboxName(String mailboxName) {this.mailboxName = mailboxName;}

    public Long getMailboxLastUid() {return mailboxLastUid;}

    public void setMailboxLastUid(Long mailboxLastUid) {this.mailboxLastUid = mailboxLastUid;}

    public Long getMailboxHighestModseq() {return mailboxHighestModseq;}

    public void setMailboxHighestModseq(Long mailboxHighestModseq) {this.mailboxHighestModseq = mailboxHighestModseq;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
