package com.mvg.sky.repository;

import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.repository.entity.AccountEntity;
import java.util.Collection;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query("""
        select account
        from AccountEntity account
        inner join DomainEntity domain on domain.id = account.domainId
        where account.username = :username and domain.name = :domain
            and domain.isDeleted = false and account.isDeleted = false and account.isActive = true
    """)
    AccountEntity findAccountByEmail(String username, String domain);

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
