package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Aquafarm.Repository.AquafarmInfoRepository;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import com.example.aquafarm.Weather.Repository.WeatherInfoRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherServiceImpl {
    private final WeatherInfoRepository weatherInfoRepository;
    private final AquafarmInfoRepository aquafarmInfoRepository;
    private final ExternalApiService externalApiService;

    @Autowired
    public WeatherServiceImpl(WeatherInfoRepository weatherInfoRepository, AquafarmInfoRepository aquafarmInfoRepository, ExternalApiService externalApiService) {
        this.weatherInfoRepository = weatherInfoRepository;
        this.aquafarmInfoRepository = aquafarmInfoRepository;
        this.externalApiService = externalApiService;
    }

    public void updateWeatherInfo() {
        // aquafarm_info 테이블의 첫 번째 데이터를 가져옴
        Optional<AquafarmInfo> aquafarmInfoOptional = aquafarmInfoRepository.findFirstByOrderByAquafarmId();
        AquafarmInfo aquafarmInfo = aquafarmInfoOptional.orElse(null);

        // 주소를 위도와 경도로 변환하여 가져옴
        String address = aquafarmInfo.getAddress();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("Ciw-2ZHwSGysPtmR8BhsKw")
                .build();

        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (results.length > 0) {
            LatLng location = results[0].geometry.location;
            double latitude = location.lat;
            double longitude = location.lng;

            // 위도와 경도를 사용하여 나머지 로직 수행
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setLocationX(latitude);
            weatherInfo.setLocationY(longitude);

            // 날씨 정보 저장
            weatherInfoRepository.save(weatherInfo);
        } else {
            // 주소를 변환할 수 없는 경우 처리
            System.out.println("Failed to convert address to latitude and longitude.");
        }

        // 일몰 시간과 일출 시간을 가져옴 (예시로 고정된 값 사용)
        LocalDateTime sunriseTime = externalApiService.getSunriseTime(latitude, longitude);
        LocalDateTime sunsetTime = externalApiService.getSunsetTime(latitude, longitude);

        // Weather_info 엔티티 생성
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setLocationX(latitude);
        weatherInfo.setLocationY(longitude);
        weatherInfo.setSunrise(sunriseTime);
        weatherInfo.setSunset(sunsetTime);

        // Weather_info 테이블에 저장
        weatherInfoRepository.save(weatherInfo);
    }
}