package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesMailboxAnnotation;
import com.mvg.sky.james.entity.JamesMailboxAnnotationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JamesMailboxAnnotationRepository extends JpaRepository<JamesMailboxAnnotation, JamesMailboxAnnotationId> {}
