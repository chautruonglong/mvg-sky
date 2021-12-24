package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_quota_currentquota")
public class JamesQuotaCurrentquota {
    @Id
    @Column(name = "currentquota_quotaroot", nullable = false)
    private String id;

    @Column(name = "currentquota_messagecount")
    private Long currentquotaMessagecount;

    @Column(name = "currentquota_size")
    private Long currentquotaSize;

    public Long getCurrentquotaSize() {return currentquotaSize;}

    public void setCurrentquotaSize(Long currentquotaSize) {this.currentquotaSize = currentquotaSize;}

    public Long getCurrentquotaMessagecount() {return currentquotaMessagecount;}

    public void setCurrentquotaMessagecount(Long currentquotaMessagecount) {this.currentquotaMessagecount = currentquotaMessagecount;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
