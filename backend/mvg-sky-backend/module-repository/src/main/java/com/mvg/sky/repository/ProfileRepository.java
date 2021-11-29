package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.ProfileEntity;
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
public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
    @Query("""
        select p
        from ProfileEntity p
        inner join AccountEntity a on a.id = p.accountId
        where p.isDeleted = false and a.isDeleted = false
    """)
    List<ProfileEntity> findAllByIsDeletedFalse(Pageable pageable);

    @Query("""
        select p
        from ProfileEntity p
        inner join AccountEntity a on a.id = p.accountId
        where p.isDeleted = false and a.isDeleted = false and p.accountId in :accountIds
    """)
    List<ProfileEntity> findAllByAccountIdInAndIsDeletedFalse(Collection<UUID> accountIds, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update  ProfileEntity p set p.isDeleted = true where p.isDeleted = false and p.id = :id")
    Integer deleteByIdAndIsDeletedFalse(UUID id);
}
