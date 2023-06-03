package com.example.aquafarm.Weather.Repository;

import com.example.aquafarm.Weather.Domain.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Integer> {
    List<WeatherInfo> findByLocationXAndLocationY(Double locationX, Double locationY);

    List<WeatherInfo> findByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    Optional<WeatherInfo> findFirstByOrderByTimeDesc();
}
