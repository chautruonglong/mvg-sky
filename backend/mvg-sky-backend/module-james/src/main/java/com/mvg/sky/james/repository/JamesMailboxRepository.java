package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMailboxRepository extends JpaRepository<JamesMailbox, Long> {
    @Query("""
        select b from JamesMailbox b where upper(b.mailboxName) = upper(:mailbox)
    """)
    JamesMailbox findJamesMailboxByMailboxName(String mailbox);
}
