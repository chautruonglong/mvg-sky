package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.FolderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {}
