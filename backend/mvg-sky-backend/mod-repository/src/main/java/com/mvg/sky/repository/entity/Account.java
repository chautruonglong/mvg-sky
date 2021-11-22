package com.mvg.sky.repository.entity;

import com.mvg.sky.repository.constant.Role;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
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
        defaultForType = Role[].class,
        parameters = {@Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "text")}
    )
})
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "isActive", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @Type(type = "roles-enum")
    @Column(name = "roles", columnDefinition = "text[]")
    private Role[] roles;
}
