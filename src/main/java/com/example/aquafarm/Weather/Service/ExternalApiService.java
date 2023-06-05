package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.DTO.WeatherDTO;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface ExternalApiService {
    <T> ResponseEntity<T> getApiResponse(String apiUrl, Class<T> responseType);

    WeatherResponse getWeatherData(String apiUrl);

    String getSunriseTimeFromWeatherAPI(double latitude, double longitude);

    String getSunsetTimeFromWeatherAPI(double latitude, double longitude);

    WeatherDTO getWeatherDTO(String location);
}