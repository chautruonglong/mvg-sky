package com.mvg.sky.account.security;

import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record UserDetailsServiceImpl(AccountRepository accountRepository) implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) throws RuntimeException {
        int at = email.indexOf('@');
        String domain = email.substring(at + 1);

        AccountDomainDto accountDomainDto = accountRepository.findAccountDomainByEmail(email, domain);

        if(accountDomainDto == null) {
            throw new UsernameNotFoundException("User do not exists or unactivated");
        }

        log.info("Query account result {}", accountDomainDto.getAccountEntity());
        return new UserPrincipal(accountDomainDto);
    }
}
