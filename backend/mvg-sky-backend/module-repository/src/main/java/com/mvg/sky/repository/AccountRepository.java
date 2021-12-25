package com.mvg.sky.repository;

import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.AccountEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query("""
        select new com.mvg.sky.repository.dto.query.AccountDomainDto(a, d)
        from AccountEntity a
        inner join DomainEntity d on d.id = a.domainId
        where a.username = :username and d.name = :domain
            and d.isDeleted = false and a.isDeleted = false and a.isActive = true
    """)
    AccountDomainDto findAccountDomainByEmail(String username, String domain);

    @Transactional
    @Modifying
    @Query("update AccountEntity a set a.isDeleted = true where a.id = :accountId and a.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID accountId);

    @Query("""
        select a
        from AccountEntity a
        where a.isDeleted = false and (:domainIds is null or cast(a.domainId as org.hibernate.type.UUIDCharType) in :domainIds)
    """)
    List<AccountEntity> findAllAccounts(Collection<UUID> domainIds, Pageable pageable);

    @Query("""
        select new com.mvg.sky.repository.dto.query.AccountDomainDto(a, d)
        from AccountEntity a
        inner join DomainEntity d on d.id = a.domainId
        where a.isDeleted = false and d.isDeleted = false and a.isActive = true and a.id = :accountId
    """)
    AccountDomainDto findAccountById(UUID accountId);

    AccountEntity findByUsernameAndIsDeletedTrue(String username);

    AccountEntity findByUsernameAndIsDeletedFalseAndIsActiveTrue(String username);

    AccountEntity findByIdAndIsDeletedFalseAndIsActiveTrue(UUID accountId);

    List<AccountEntity> findAllByUsernameInAndIsDeletedFalseAndIsActiveTrue(Collection<String> usernames);
}
