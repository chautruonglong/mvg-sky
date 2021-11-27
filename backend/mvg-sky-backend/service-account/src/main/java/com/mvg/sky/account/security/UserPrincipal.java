package com.mvg.sky.account.security;

import com.mvg.sky.repository.dto.query.AccountDomainDto;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserPrincipal(AccountDomainDto accountDomainDto) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(accountDomainDto.getAccountEntity().getRoles())
            .map(roleEnumeration -> new SimpleGrantedAuthority(roleEnumeration.name()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return accountDomainDto.getAccountEntity().getPassword();
    }

    @Override
    public String getUsername() {
        return accountDomainDto.getAccountEntity().getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountDomainDto.getAccountEntity().getIsDeleted();
    }

    @Override
    public boolean isEnabled() {
        return accountDomainDto.getAccountEntity().getIsActive();
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
