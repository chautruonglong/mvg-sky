package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.EmailEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {}
