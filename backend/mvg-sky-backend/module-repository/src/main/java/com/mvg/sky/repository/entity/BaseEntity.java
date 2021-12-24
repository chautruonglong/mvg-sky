package com.mvg.sky.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {
    @Id
    @Builder.Default
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id = UUID.randomUUID();

    @CreatedDate
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "createdAt", columnDefinition = "timestamp default now()")
    private Date createdAt = new Date();

    @LastModifiedDate
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "updatedAt", columnDefinition = "timestamp default now()")
    private Date updatedAt = new Date();

    @Builder.Default
    @Column(name = "isDeleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }
}
