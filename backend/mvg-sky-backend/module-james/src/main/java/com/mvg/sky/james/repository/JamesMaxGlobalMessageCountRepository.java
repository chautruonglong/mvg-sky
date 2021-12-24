package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMaxGlobalMessageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMaxGlobalMessageCountRepository extends JpaRepository<JamesMaxGlobalMessageCount, String> {}
