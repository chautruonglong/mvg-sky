package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "james_mail_userflag")
public class JamesMailUserflag {
    @Id
    @Column(name = "userflag_id", nullable = false)
    private Long id;

    @Column(name = "userflag_name", nullable = false, length = 500)
    private String userflagName;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "mailbox_id", referencedColumnName = "mailbox_id"), @JoinColumn(name = "mail_uid", referencedColumnName = "mail_uid")})
    private JamesMail jamesMail;

    public JamesMail getJamesMail() {return jamesMail;}

    public void setJamesMail(JamesMail jamesMail) {this.jamesMail = jamesMail;}

    public String getUserflagName() {return userflagName;}

    public void setUserflagName(String userflagName) {this.userflagName = userflagName;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
