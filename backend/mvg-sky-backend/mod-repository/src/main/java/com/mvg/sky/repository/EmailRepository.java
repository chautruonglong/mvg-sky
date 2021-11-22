package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.Email;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {}
