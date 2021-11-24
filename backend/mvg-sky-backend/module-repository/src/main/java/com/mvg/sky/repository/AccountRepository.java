package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.AccountEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT a FROM AccountEntity a INNER JOIN DomainEntity d ON a.domainId = d.id")
    AccountEntity findAccountByEmail(String email);
}
