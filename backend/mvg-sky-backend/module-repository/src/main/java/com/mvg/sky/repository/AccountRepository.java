package com.mvg.sky.repository;

import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.AccountEntity;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query("""
        select new com.mvg.sky.repository.dto.query.AccountDomainDto(a, d)
        from AccountEntity a
        inner join DomainEntity d on d.id = a.domainId
        where a.username = :username and d.name = :domain
            and d.isDeleted = false and a.isDeleted = false and a.isActive = true
    """)
    AccountDomainDto findAccountByEmail(String username, String domain);

    @Transactional
    @Modifying
    @Query(value = """
        insert into accounts(username, password, roles, domainid)
        values(:username, :password, (:roles), (
            select domains.id from domains where domains.name = :domain and domains.isdeleted = false)
        )
    """, nativeQuery = true)
    void createAccount(String username, String password, String roles, String domain);
}
