package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.DTO.WeatherDTO;
import com.example.aquafarm.Weather.Response.ExternalApiException;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Override
    public WeatherResponse getWeatherData(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(apiUrl, WeatherResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ExternalApiException("Failed to retrieve weather data from API");
        }
    }

    @Override
    public LocalDateTime getSunriseTimeFromWeatherAPI ( double latitude, double longitude){
        String apiUrl = weatherApiUrl + "/sunrise?lat=" + latitude + "&lon=" + longitude;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(apiUrl, WeatherResponse.class);
        WeatherResponse weatherResponse = response.getBody();

        if (weatherResponse != null && weatherResponse.getStatus().equals("success")) {
            // 일출 시간을 LocalDateTime으로 변환하여 반환
            String sunriseTimeStr = weatherResponse.getSunrise();
            LocalDateTime sunriseTime = LocalDateTime.parse(sunriseTimeStr);
            return LocalDateTime.of(LocalDate.now(), sunriseTime);
        }

        // 기상청 API 호출에 실패하거나 데이터가 없는 경우
        throw new ExternalApiException("Failed to retrieve sunrise time from Weather API");
    }

    @Override
    public LocalTime getSunsetTimeFromWeatherAPI ( double latitude, double longitude){
        String apiUrl = weatherApiUrl + "/sunset?lat=" + latitude + "&lon=" + longitude;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(apiUrl, WeatherResponse.class);
        WeatherResponse weatherResponse = response.getBody();

        if (weatherResponse != null && weatherResponse.getStatus().equals("success")) {
            // 일몰 시간을 LocalTime으로 변환하여 반환
            String sunsetTimeStr = weatherResponse.getSunset();
            LocalTime sunsetTime = LocalTime.parse(sunsetTimeStr);
            return sunsetTime;
        }

        // 기상청 API 호출에 실패하거나 데이터가 없는 경우
        throw new ExternalApiException("Failed to retrieve sunset time from Weather API");

    }

    @Override
    public WeatherDTO getWeatherDTO (String location){
        // 기상청 API를 사용하여 날씨 데이터를 가져오는 로직 작성
        // ...

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(weatherApiUrl, WeatherApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            WeatherResponse apiResponse = response.getBody();
            // API 응답 데이터를 매핑하여 WeatherDTO 객체 생성
            WeatherDTO weatherDTO = new WeatherDTO();
            // ...
            return weatherDTO;
        } else {
            throw new ExternalApiException("Failed to retrieve weather data from API");
        }
    }
}
