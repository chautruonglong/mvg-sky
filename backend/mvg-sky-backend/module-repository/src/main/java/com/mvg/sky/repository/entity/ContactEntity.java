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
@Table(name = "contacts")
public class ContactEntity extends BaseEntity {
    @Column(name = "yourId", columnDefinition = "uuid")
    private UUID yourId;

    @Column(name = "partnerId", columnDefinition = "uuid")
    private UUID partnerId;
}
