package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "james_mailbox_annotation")
public class JamesMailboxAnnotation {
    @EmbeddedId
    private JamesMailboxAnnotationId id;

    @Column(name = "value")
    private String value;

    public String getValue() {return value;}

    public void setValue(String value) {this.value = value;}

    public JamesMailboxAnnotationId getId() {return id;}

    public void setId(JamesMailboxAnnotationId id) {this.id = id;}
}
