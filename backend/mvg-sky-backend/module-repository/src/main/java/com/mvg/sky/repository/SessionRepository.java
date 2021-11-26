package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.SessionEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
    List<SessionEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<SessionEntity> findAllByAccountIdInAndIsDeletedFalse(Collection<UUID> accountIds, Pageable pageable);

    SessionEntity findByIdAndIsDeletedFalse(UUID id);

    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.id = :id")
    void deleteById(@NonNull UUID id);

    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true where s.id in :accountIds")
    void deleteAllByAccountIds(Collection<UUID> accountIds);

    @Modifying
    @Query("update SessionEntity s set s.isDeleted = true")
    void deleteAll();
}
