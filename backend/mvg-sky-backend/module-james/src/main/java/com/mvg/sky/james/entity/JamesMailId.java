package com.mvg.sky.james.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class JamesMailId implements Serializable {
    private static final long serialVersionUID = 6764187401981464217L;
    @Column(name = "mailbox_id", nullable = false)
    private Long mailboxId;
    @Column(name = "mail_uid", nullable = false)
    private Long mailUid;

    public Long getMailUid() {return mailUid;}

    public void setMailUid(Long mailUid) {this.mailUid = mailUid;}

    public Long getMailboxId() {return mailboxId;}

    public void setMailboxId(Long mailboxId) {this.mailboxId = mailboxId;}

    @Override
    public int hashCode() {
        return Objects.hash(mailboxId, mailUid);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JamesMailId entity = (JamesMailId) o;
        return Objects.equals(this.mailboxId, entity.mailboxId) && Objects.equals(this.mailUid, entity.mailUid);
    }
}
