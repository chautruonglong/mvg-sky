package com.mvg.sky.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TypeDefs({
    @TypeDef(
        name = "roles-enum",
        typeClass = EnumArrayType.class,
        defaultForType = RoleEnumeration[].class,
        parameters = {@Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "text")}
    )
})
@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {
    @Column(name = "domainId", nullable = false, columnDefinition = "uuid")
    private UUID domainId;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "text")
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @Builder.Default
    @Column(name = "isActive", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Type(type = "roles-enum")
    @Column(name = "roles", columnDefinition = "text[]")
    private RoleEnumeration[] roles;
}
