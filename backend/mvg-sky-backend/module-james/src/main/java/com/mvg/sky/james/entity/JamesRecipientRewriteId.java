package com.mvg.sky.james.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class JamesRecipientRewriteId implements Serializable {
    private static final long serialVersionUID = 6524874210366890535L;
    @Column(name = "domain_name", nullable = false, length = 100)
    private String domainName;
    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getDomainName() {return domainName;}

    public void setDomainName(String domainName) {this.domainName = domainName;}

    @Override
    public int hashCode() {
        return Objects.hash(domainName, userName);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JamesRecipientRewriteId entity = (JamesRecipientRewriteId) o;
        return Objects.equals(this.domainName, entity.domainName) && Objects.equals(this.userName, entity.userName);
    }
}
