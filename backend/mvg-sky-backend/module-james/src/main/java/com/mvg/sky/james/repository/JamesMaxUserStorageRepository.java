package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMaxUserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesMaxUserStorageRepository extends JpaRepository<JamesMaxUserStorage, String> {}
