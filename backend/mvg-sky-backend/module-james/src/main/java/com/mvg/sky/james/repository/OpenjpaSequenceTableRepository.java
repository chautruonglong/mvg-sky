package com.mvg.sky.james.repository;

import com.mvg.sky.james.entity.OpenjpaSequenceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenjpaSequenceTableRepository extends JpaRepository<OpenjpaSequenceTable, Integer> {}
