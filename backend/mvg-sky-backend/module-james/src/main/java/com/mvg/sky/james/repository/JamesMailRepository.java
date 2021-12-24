package com.mvg.sky.james.repository;

import com.mvg.sky.james.dto.JamesMailDto;
import com.mvg.sky.james.entity.JamesMail;
import com.mvg.sky.james.entity.JamesMailId;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMailRepository extends JpaRepository<JamesMail, JamesMailId> {
    @Query("""
        select new com.mvg.sky.james.dto.JamesMailDto(m, a.id)
        from JamesMail m
        inner join JamesMailbox b on b.id = m.id.mailboxId
        inner join JamesUser u on u.id = b.userName
        inner join AccountEntity a on a.username = u.id
        where u.id = :username and upper(b.mailboxName) = upper(:mailbox) and m.mailIsDeleted = false
    """)
    List<JamesMailDto> fetchMails(String username, String mailbox, Pageable pageable);

    @Query("""
        select new com.mvg.sky.james.dto.JamesMailDto(m, a.id)
        from JamesMail m
        inner join JamesMailbox b on b.id = m.id.mailboxId
        inner join JamesUser u on u.id = b.userName
        inner join AccountEntity a on a.username = u.id
        where m.id.mailUid = :mailId and m.mailIsDeleted = false
    """)
    JamesMailDto fetchMailById(Long mailId);

    @Transactional
    @Modifying
    @Query("""
        update JamesMail m
        set m.mailIsAnswered = case when (upper(:status) = 'ANSWRED') then true else false end,
            m.mailIsDraft = case when (upper(:status) = 'DRAFT') then true else false end,
            m.mailIsRecent = case when (upper(:status) = 'RECENT') then true else false end,
            m.mailIsDeleted = case when (upper(:status) = 'DELETED') then true else false end,
            m.mailIsFlagged = case when (upper(:status) = 'FLAGGED') then true else false end,
            m.mailIsSeen = case when (upper(:status) = 'SEEN') then true else false end
        where m.id.mailUid = :mailId and m.mailIsDeleted = false
    """)
    Integer updateStatus(Long mailId, String status);

    @Transactional
    @Modifying
    @Query("""
        update JamesMail m set m.mailIsDeleted = true where m.id.mailUid = :mailId and m.mailIsDeleted = false
    """)
    Integer deleteJamesMailByMailId(Long mailId);

    @Transactional
    @Modifying
    @Query("""
        update JamesMail m set m.mailIsDeleted = true where m.id.mailUid in :mailIds and m.mailIsDeleted = false
    """)
    Integer deleteBatchJamesMailsByMailIds(Collection<Long> mailIds);
}
