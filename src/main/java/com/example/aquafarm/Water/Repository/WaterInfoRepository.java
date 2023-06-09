package com.example.aquafarm.Water.Repository;


import com.example.aquafarm.Water.Domain.WaterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WaterInfoRepository extends JpaRepository<WaterInfo, Integer> {
    // 저장소 관련 메서드 정의

    List<WaterInfo> findAllByDateBetween(int aquafarm_id,Timestamp start, Timestamp end);

}