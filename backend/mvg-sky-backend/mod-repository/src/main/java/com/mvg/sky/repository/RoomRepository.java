package com.mvg.sky.repository;

import com.mvg.sky.repository.entity.RoomEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {}
