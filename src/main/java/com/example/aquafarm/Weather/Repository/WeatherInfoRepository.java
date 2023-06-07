package com.example.aquafarm.Weather.Repository;

import com.example.aquafarm.Weather.Domain.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Integer> {
    List<WeatherInfo> findByLocationXAndLocationY(Double locationX, Double locationY);

    List<WeatherInfo> findByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    Optional<WeatherInfo> findFirstByOrderByTimeDesc();
    Optional<WeatherInfo> findByAquafarmId(int aquafarmId);
  //  WeatherInfo findByAddress(String address);
    Optional<WeatherInfo> findByAddressAndTime(String address, LocalDate time);
    Optional<WeatherInfo> findByTime(LocalDate time);

    WeatherInfo findByAllAddress(String address);
}

