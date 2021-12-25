package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesUserRepository extends JpaRepository<JamesUser, String> {}
