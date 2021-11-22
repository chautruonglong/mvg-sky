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
@Table(name = "folders")
public class Folder extends BaseEntity {
    @Column(name = "accountId", nullable = false)
    private UUID accountId;

    @Column(name = "parentId")
    private UUID parentId;

    @Column(name = "name")
    private String name;
}
