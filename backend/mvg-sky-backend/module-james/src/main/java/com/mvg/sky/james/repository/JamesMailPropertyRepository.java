package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMailProperty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMailPropertyRepository extends JpaRepository<JamesMailProperty, Long> {
    @Query("""
        select p
        from JamesMailProperty p
        where p.jamesMail.id.mailUid = :mailId and p.jamesMail.id.mailboxId = :mailboxId
    """)
    List<JamesMailProperty> findByJamesMailId(Long mailId, Long mailboxId);
}
