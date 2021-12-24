package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesQuotaCurrentquota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesQuotaCurrentquotaRepository extends JpaRepository<JamesQuotaCurrentquota, String> {}
