package com.example.aquafarm.Weather.Controller;

import com.example.aquafarm.Weather.Service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/weather")
public class WeatherInfoController {
    private final WeatherInfoService weatherInfoService;

    @Autowired
    public WeatherInfoController(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping(value = "/sunrise-sunset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getSunriseAndSunsetTimeToday() {
        Optional<String> sunriseTimeOptional = weatherInfoService.getSunriseTimeToday();
        Optional<String> sunsetTimeOptional = weatherInfoService.getSunsetTimeToday();

        if (sunriseTimeOptional.isPresent() && sunsetTimeOptional.isPresent()) {
            String sunrise = sunriseTimeOptional.get();
            String sunset = sunsetTimeOptional.get();

            Map<String, String> sunriseSunsetMap = new HashMap<>();
            sunriseSunsetMap.put("sunrise", sunrise);
            sunriseSunsetMap.put("sunset", sunset);

            return ResponseEntity.ok(sunriseSunsetMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}