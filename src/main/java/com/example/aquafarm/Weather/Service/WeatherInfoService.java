package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.Domain.WeatherInfo;
import com.example.aquafarm.Weather.Repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherInfoService {
    private final WeatherInfoRepository weatherInfoRepository;

    @Autowired
    public WeatherInfoService(WeatherInfoRepository weatherInfoRepository) {
        this.weatherInfoRepository = weatherInfoRepository;
    }

    public Optional<String> getSunriseTimeToday() {
        LocalDate today = LocalDate.now();
        Optional<WeatherInfo> weatherInfoOptional = weatherInfoRepository.findByTime(today);
        return weatherInfoOptional.map(WeatherInfo::getSunrise);
    }

    public Optional<String> getSunsetTimeToday() {
        LocalDate today = LocalDate.now();
        Optional<WeatherInfo> weatherInfoOptional = weatherInfoRepository.findByTime(today);
        return weatherInfoOptional.map(WeatherInfo::getSunset);
    }
}
