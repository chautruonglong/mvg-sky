package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_mail_repos")
public class JamesMailRepo {
    @Id
    @Column(name = "mail_repo_name", nullable = false, length = 1024)
    private String id;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
