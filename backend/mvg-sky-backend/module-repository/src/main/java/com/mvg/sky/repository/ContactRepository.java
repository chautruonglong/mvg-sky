package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.ContactEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {}
