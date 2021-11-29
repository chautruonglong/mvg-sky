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
    AccountDomainDto findAccountByEmail(String username, String domain);

    @Transactional
    @Modifying
    @Query("update AccountEntity a set a.isDeleted = true where a.id = :accountId and a.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID accountId);

    List<AccountEntity> findAllByDomainIdInAndIsDeletedFalse(Collection<UUID> domainsIds, Pageable pageable);

    List<AccountEntity> findAllByIsDeletedFalse(Pageable pageable);
}