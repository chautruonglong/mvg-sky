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
    @Transactional
    @Modifying
    @Query("update MessageEntity m set m.isDeleted = true where m.id = :messageId and m.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID messageId);

    @Query("""
        select m
        from MessageEntity m
        where m.isDeleted = false and m.isInSchedule = false
            and (:roomIds is null or cast(m.roomId as org.hibernate.type.UUIDCharType) in :roomIds)
            and (:types is null or m.type in :types)
    """)
    List<MessageEntity> findALlMessages(Collection<UUID> roomIds, Collection<MessageEnumeration> types, Pageable pageable);
}
