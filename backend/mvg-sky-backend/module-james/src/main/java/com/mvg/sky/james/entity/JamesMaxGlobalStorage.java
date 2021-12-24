package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_max_global_storage")
public class JamesMaxGlobalStorage {
    @Id
    @Column(name = "quotaroot_id", nullable = false)
    private String id;

    @Column(name = "value")
    private Long value;

    public Long getValue() {return value;}

    public void setValue(Long value) {this.value = value;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
