package com.mvg.sky.repository;

import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.dto.query.MessageDto;
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
        select new com.mvg.sky.repository.dto.query.MessageDto(m, _m)
        from MessageEntity m
        left join MessageEntity _m on m.id = _m.threadId and _m.threadId is not null and _m.isDeleted = false
        where m.isDeleted = false and m.isInSchedule = false and m.threadId is null
            and (:roomIds is null or cast(m.roomId as org.hibernate.type.UUIDCharType) in :roomIds)
            and (:types is null or m.type in :types)
    """)
    List<MessageDto> findALlMessages(Collection<UUID> roomIds, Collection<MessageEnumeration> types, Pageable pageable);
}
