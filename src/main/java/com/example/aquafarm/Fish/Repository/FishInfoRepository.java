package com.example.aquafarm.Fish.Repository;

import com.example.aquafarm.Fish.Domain.FishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FishInfoRepository extends JpaRepository<FishInfo, Integer> {
    @Query("SELECT COUNT(fi) FROM FishInfo fi WHERE fi.foodRecord BETWEEN :startDateTime AND :endDateTime")
    int countByFoodRecordBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

    List<FishInfo> findByFoodRecordBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}