package com.mvg.sky.repository;

import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.entity.MessageEntity;
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
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findAllByRoomIdInAndIsDeletedFalseAndIsInScheduleFalse(Collection<UUID> roomIds, Pageable pageable);

    List<MessageEntity> findAllByRoomIdInAndTypeInAndIsDeletedFalseAndIsInScheduleFalse(Collection<UUID> roomIds, Collection<MessageEnumeration> types, Pageable pageable);

    List<MessageEntity> findAllByIsDeletedFalseAndIsInScheduleFalse(Pageable pageable);

    List<MessageEntity> findAllByTypeInAndIsDeletedFalseAndIsInScheduleFalse(Collection<MessageEnumeration> types, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update MessageEntity m set m.isDeleted = true where m.id = :messageId and m.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID messageId);
}
