package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMailUserflag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMailUserflagRepository extends JpaRepository<JamesMailUserflag, Long> {}
