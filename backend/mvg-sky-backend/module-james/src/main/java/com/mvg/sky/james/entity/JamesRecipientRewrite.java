package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "james_recipient_rewrite")
public class JamesRecipientRewrite {
    @EmbeddedId
    private JamesRecipientRewriteId id;

    @Column(name = "target_address", nullable = false, length = 100)
    private String targetAddress;

    public String getTargetAddress() {return targetAddress;}

    public void setTargetAddress(String targetAddress) {this.targetAddress = targetAddress;}

    public JamesRecipientRewriteId getId() {return id;}

    public void setId(JamesRecipientRewriteId id) {this.id = id;}
}
