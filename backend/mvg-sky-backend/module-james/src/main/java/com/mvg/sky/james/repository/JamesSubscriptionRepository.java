package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesSubscriptionRepository extends JpaRepository<JamesSubscription, Long> {}
