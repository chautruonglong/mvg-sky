package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMaxUserMessageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMaxUserMessageCountRepository extends JpaRepository<JamesMaxUserMessageCount, String> {}
