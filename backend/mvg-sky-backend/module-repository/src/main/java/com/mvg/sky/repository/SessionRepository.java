package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.SessionEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
    @Query("""
        select s
        from SessionEntity s
        where s.isDeleted = false and (:accountIds is null or cast(s.accountId as org.hibernate.type.UUIDCharType) in :accountIds)
    """)
    List<SessionEntity> findAllSessions(Collection<UUID> accountIds, Pageable pageable);

    SessionEntity findByIdAndIsDeletedFalse(UUID id);

    SessionEntity findByTokenAndIsDeletedFalse(String token);

    @Transactional
    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.accountId = :accountId and s.token = :token and s.isDeleted = false")
    Integer deleteByAccountIdAndTokenAndIsDeletedFalse(UUID accountId, String token);

    @Transactional
    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.accountId = :accountId and s.isDeleted = false")
    Integer deleteAllByAccountIdAndIsDeletedFalse(UUID accountId);

    @Transactional
    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.id = :id and s.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(@NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.id in :accountIds and s.isDeleted = false")
    Integer deleteAllByAccountIdInAndIsDeletedFalse(Collection<UUID> accountIds);

    @Transactional
    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.isDeleted = false")
    Integer deleteAllAndIsDeletedFalse();
}
