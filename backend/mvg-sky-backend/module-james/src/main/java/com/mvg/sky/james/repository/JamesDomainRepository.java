package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesDomainRepository extends JpaRepository<JamesDomain, String> {}
