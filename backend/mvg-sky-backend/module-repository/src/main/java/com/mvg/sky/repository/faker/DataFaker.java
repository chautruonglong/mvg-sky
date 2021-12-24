package com.mvg.sky.repository.faker;

import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.DomainRepository;
import com.mvg.sky.repository.ProfileRepository;
import com.mvg.sky.repository.RoomAccountRepository;
import com.mvg.sky.repository.RoomRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import net.andreinc.mockneat.MockNeat;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataFaker {
    private final DomainRepository domainRepository;
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    private final RoomRepository roomRepository;
    private final RoomAccountRepository roomAccountRepository;

//    @PostConstruct
    public void fakeData() {
        final MockNeat mockNeat = MockNeat.secure();
        final int NUM_OF_DOMAINS = 20;
        final int NUM_OF_ACCOUNTS_PER_DOMAIN = 20;

        Set<String> domains = new HashSet<>(mockNeat.domains().list(NUM_OF_DOMAINS).get());
        List<DomainEntity> domainEntities = domains.stream().map(domain -> DomainEntity.builder().name(domain).build()).collect(Collectors.toList());
        List<DomainEntity> savedDomainEntities = domainRepository.saveAll(domainEntities);

        savedDomainEntities.forEach(domainEntity -> {
            Set<String> emails = new HashSet<>(mockNeat.emails().domain(domainEntity.getName()).list(NUM_OF_ACCOUNTS_PER_DOMAIN).get());
            List<AccountEntity> accountEntities = emails.stream().map(email -> AccountEntity.builder()
                .domainId(domainEntity.getId())
                .username(email.substring(0, email.indexOf('@')))
                .password(mockNeat.passwords().get())
                .roles(new RoleEnumeration[] {RoleEnumeration.EMPLOYEE})
                .build()
            ).collect(Collectors.toList());
            List<AccountEntity> savedAccountEntityList = accountRepository.saveAll(accountEntities);
        });
    }
}
