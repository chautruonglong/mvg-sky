package com.mvg.sky.repository.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "sessions")
public class SessionEntity extends BaseEntity {
    @Column(name = "accountId", nullable = false, columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "token", nullable = false, columnDefinition = "text")
    private String token;
}
