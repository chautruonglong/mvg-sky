package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMaxGlobalStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMaxGlobalStorageRepository extends JpaRepository<JamesMaxGlobalStorage, String> {}
