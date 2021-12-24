package com.mvg.sky.james.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class JamesMailboxAnnotationId implements Serializable {
    private static final long serialVersionUID = -2183583254435346327L;
    @Column(name = "annotation_key", nullable = false, length = 200)
    private String annotationKey;
    @Column(name = "mailbox_id", nullable = false)
    private Long mailboxId;

    public Long getMailboxId() {return mailboxId;}

    public void setMailboxId(Long mailboxId) {this.mailboxId = mailboxId;}

    public String getAnnotationKey() {return annotationKey;}

    public void setAnnotationKey(String annotationKey) {this.annotationKey = annotationKey;}

    @Override
    public int hashCode() {
        return Objects.hash(mailboxId, annotationKey);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JamesMailboxAnnotationId entity = (JamesMailboxAnnotationId) o;
        return Objects.equals(this.mailboxId, entity.mailboxId) && Objects.equals(this.annotationKey, entity.annotationKey);
    }
}
