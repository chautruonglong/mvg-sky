package com.mvg.sky.repository;

import com.mvg.sky.repository.dto.query.ProfileContactDto;
import com.mvg.sky.repository.entity.ContactEntity;
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
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
    @Query("""
        select new com.mvg.sky.repository.dto.query.ProfileContactDto(p, c)
        from ProfileEntity p
        left join ContactEntity c on c.partnerId = p.id
        inner join AccountEntity a on a.id = p.accountId
        inner join DomainEntity d on d.id = a.domainId
        where p.isDeleted = false and d.isDeleted = false and a.isDeleted = false and a.isActive = true
            and (
                (:profileUuids is null and :domainUuids is null)
                or (
                    (:profileUuids is not null and cast(c.yourId as org.hibernate.type.UUIDCharType) in :profileUuids)
                    or (:domainUuids is not null and cast(d.id as org.hibernate.type.UUIDCharType) in :domainUuids)
                )
            )
    """)
    List<ProfileContactDto> findAllContacts(Collection<UUID> profileUuids, Collection<UUID> domainUuids, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ContactEntity c set c.isDeleted = true where c.id = :contactId and c.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID contactId);

    @Transactional
    @Modifying
    @Query("""
        update ContactEntity c set c.isDeleted = true where c.isDeleted = false
            and (:profileIds is null or cast(c.yourId as org.hibernate.type.UUIDCharType) in :profileIds)
    """)
    Integer clearAll(Collection<UUID> profileIds);
}
