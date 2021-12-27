package com.mvg.sky.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.net.UrlEscapers;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "profiles")
public class ProfileEntity extends BaseEntity {
    @Column(name = "accountId", nullable = false, columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "firstName", columnDefinition = "text")
    private String firstName;

    @Column(name = "lastName", columnDefinition = "text")
    private String lastName;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "mobile", columnDefinition = "text")
    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", columnDefinition = "date")
    private LocalDate birthday;

    @Builder.Default
    @Column(name = "location", columnDefinition = "text default 'Danang/Vietnam'")
    private String location = "Danang/Vietnam";

    @Column(name = "avatar", columnDefinition = "text")
    private String avatar;

    public String getAvatar() {
        if(avatar != null) {
            return UrlEscapers.urlFragmentEscaper().escape("/api/accounts-resources/avatar/" + avatar);
        }
        return null;
    }

    @JsonIgnore
    public String getOriginalAvatar() {
        return avatar;
    }
}
