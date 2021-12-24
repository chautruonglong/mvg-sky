package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "james_subscription", indexes = {@Index(name = "u_jms_ptn_user_name", columnList = "user_name, mailbox_name", unique = true)})
public class JamesSubscription {
    @Id
    @Column(name = "subscription_id", nullable = false)
    private Long id;

    @Column(name = "mailbox_name", nullable = false, length = 100)
    private String mailboxName;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getMailboxName() {return mailboxName;}

    public void setMailboxName(String mailboxName) {this.mailboxName = mailboxName;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
