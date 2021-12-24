package com.mvg.sky.repository;

import com.mvg.sky.repository.dto.query.MemberDto;
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

    @Query("""
        select new com.mvg.sky.repository.dto.query.MemberDto(ra, p)
        from RoomAccountEntity ra
        inner join ProfileEntity p on p.accountId = ra.accountId
        where ra.isDeleted = false and p.isDeleted = false
            and (:roomIds is null or cast(ra.roomId as org.hibernate.type.UUIDCharType) in :roomIds)
    """)
    List<MemberDto> findAllMembers(Collection<UUID> roomIds, Pageable pageable);
}
