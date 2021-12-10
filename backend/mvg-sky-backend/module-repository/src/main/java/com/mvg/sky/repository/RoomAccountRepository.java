package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.RoomAccountEntity;
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
public interface RoomAccountRepository extends JpaRepository<RoomAccountEntity, UUID> {
    @Transactional
    @Modifying
    @Query("update RoomAccountEntity rc set rc.isDeleted = true where rc.id = :id and rc.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID id);

    List<RoomAccountEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<RoomAccountEntity> findAllByRoomIdInAndIsDeletedFalse(Collection<UUID> roomIds, Pageable pageable);
}
