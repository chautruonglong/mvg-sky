package com.mvg.sky.repository.entity;

import com.mvg.sky.common.enumeration.EmailEnumeration;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "mails")
public class MailEntity extends BaseEntity {
    @Column(name = "accountId", nullable = false, columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "folderId", nullable = false, columnDefinition = "uuid")
    private UUID folderId;

    @Column(name = "fileName", nullable = false, columnDefinition = "text")
    private String fileName;

    @Column(name = "flag", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private EmailEnumeration flag;
}
