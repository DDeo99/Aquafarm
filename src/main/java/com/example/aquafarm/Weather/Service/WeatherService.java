package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Weather.Domain.WeatherInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherService {

    void updateWeatherInfo();

    void saveWeatherInfo(WeatherInfo weatherInfo);

    void updateWeatherInfo(WeatherInfo weatherInfo);

    WeatherInfo getWeatherInfoByAquafarmId(int aquafarmId);

    WeatherInfo getWeatherInfoByAquafarm(AquafarmInfo aquafarmInfo);

    void updateWeatherInfoByAquafarmId(int aquafarmId);

    String getSunriseTimeFromWeatherAPI(double latitude, double longitude);

    String getSunsetTimeFromWeatherAPI(double latitude, double longitude);
}