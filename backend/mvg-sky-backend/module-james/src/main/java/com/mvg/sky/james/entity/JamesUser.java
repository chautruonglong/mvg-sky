package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "james_user")
public class JamesUser {
    @Id
    @Column(name = "user_name", nullable = false, length = 100)
    private String id;

    @Column(name = "password_hash_algorithm", nullable = false, length = 100)
    private String passwordHashAlgorithm;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "version")
    private Integer version;

    public Integer getVersion() {return version;}

    public void setVersion(Integer version) {this.version = version;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getPasswordHashAlgorithm() {return passwordHashAlgorithm;}

    public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {this.passwordHashAlgorithm = passwordHashAlgorithm;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
