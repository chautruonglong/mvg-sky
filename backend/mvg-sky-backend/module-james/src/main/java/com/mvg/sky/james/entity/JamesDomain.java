package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_domain")
public class JamesDomain {
    @Id
    @Column(name = "domain_name", nullable = false, length = 100)
    private String id;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
