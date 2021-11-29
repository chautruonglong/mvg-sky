package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.ContactEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
    @Query("""
        select c, p
        from ContactEntity c
        inner join ProfileEntity p on c.partnerId = p.id
        inner join AccountEntity a on a.id = p.accountId
        where c.isDeleted = false and p.isDeleted = false and a.isDeleted = false
            and a.isActive = true and c.yourId in :profileIds
    """)
    List<?> findAllByProfileIdAndIsDeletedFalse(Collection<UUID> profileIds, Pageable pageable);
}
