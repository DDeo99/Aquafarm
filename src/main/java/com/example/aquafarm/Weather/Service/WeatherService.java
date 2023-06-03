package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.Domain.WeatherInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherService {
    List<WeatherInfo> getWeatherInfoByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    WeatherInfo getLatestWeatherInfo();
}