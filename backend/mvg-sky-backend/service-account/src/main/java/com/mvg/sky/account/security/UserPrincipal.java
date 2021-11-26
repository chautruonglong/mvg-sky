package com.mvg.sky.account.security;

import com.mvg.sky.repository.entity.AccountEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserPrincipal(AccountEntity accountEntity) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(accountEntity.getRoles())
            .map(roleEnumeration -> new SimpleGrantedAuthority(roleEnumeration.name()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return accountEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return accountEntity.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountEntity.getIsDeleted();
    }

    @Override
    public boolean isEnabled() {
        return accountEntity.getIsActive();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
