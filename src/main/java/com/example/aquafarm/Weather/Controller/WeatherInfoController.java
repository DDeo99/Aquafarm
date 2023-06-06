package com.example.aquafarm.Weather.Controller;

import com.example.aquafarm.Weather.Service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherInfoController {
    private final WeatherInfoService weatherInfoService;

    @Autowired
    public WeatherInfoController(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping("/sunrise-sunset")
    public ResponseEntity<Map<String, String>> getSunriseAndSunsetTimeToday() {
        Optional<String> sunriseTimeOptional = weatherInfoService.getSunriseTimeToday();
        Optional<String> sunsetTimeOptional = weatherInfoService.getSunsetTimeToday();

        if (sunriseTimeOptional.isPresent() && sunsetTimeOptional.isPresent()) {
            String sunriseTime = sunriseTimeOptional.get();
            String sunsetTime = sunsetTimeOptional.get();

            Map<String, String> sunriseSunsetMap = new HashMap<>();
            sunriseSunsetMap.put("sunrise", sunriseTime);
            sunriseSunsetMap.put("sunset", sunsetTime);

            return ResponseEntity.ok(sunriseSunsetMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}