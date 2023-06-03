package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.DTO.WeatherDTO;
import com.example.aquafarm.Weather.Response.WeatherResponse;

import java.time.LocalDateTime;

public interface ExternalApiService {
    WeatherResponse getWeatherData(String apiUrl);

    LocalDateTime getSunriseTimeFromWeatherAPI(double latitude, double longitude);

    LocalDateTime getSunsetTimeFromWeatherAPI(double latitude, double longitude);

    WeatherDTO getWeatherDTO(String location);
}