package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.DomainEntity;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, UUID> {
    DomainEntity findByNameAndIsDeletedFalse(String name);

    DomainEntity findByNameAndIsDeletedTrue(String name);

    DomainEntity findByIdAndIsDeletedFalse(UUID id);

    @Transactional
    @Modifying
    @Query("update DomainEntity d set d.isDeleted = true where d.id = :domainId and d.isDeleted = false")
    Integer deleteByIdAndIsDeletedFalse(UUID domainId);

    List<DomainEntity> findAllByIsDeletedFalse(Pageable pageable);
}
