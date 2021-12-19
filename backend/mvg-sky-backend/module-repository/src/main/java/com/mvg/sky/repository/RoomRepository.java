package com.mvg.sky.repository;

import com.mvg.sky.repository.dto.query.RoomMessageDto;
import com.mvg.sky.repository.entity.RoomEntity;
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
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    @Query("""
        select new com.mvg.sky.repository.dto.query.RoomMessageDto(r, m)
        from RoomEntity r
        inner join RoomAccountEntity rc on rc.roomId = r.id
        inner join AccountEntity a on a.id = rc.accountId
        left join MessageEntity m on m.roomId = r.id
            and m.createdAt = (
                select max(_m.createdAt)
                from MessageEntity _m
                where m.roomId = _m.roomId
            )
        where r.isDeleted = false and a.isDeleted = false and rc.isDeleted = false and a.isActive = true
            and (:accountIds is null or cast(a.id as org.hibernate.type.UUIDCharType) in :accountIds)
        group by r, m
    """)
    List<RoomMessageDto> findAllRooms(Collection<UUID> accountIds, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update RoomEntity r set r.isDeleted = true where r.id = :id and r.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID id);
}
