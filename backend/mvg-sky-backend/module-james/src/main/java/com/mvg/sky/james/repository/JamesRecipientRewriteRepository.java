package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.JamesRecipientRewrite;
import com.mvg.sky.james.entity.JamesRecipientRewriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamesRecipientRewriteRepository extends JpaRepository<JamesRecipientRewrite, JamesRecipientRewriteId> {}
