package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Aquafarm.Repository.AquafarmInfoRepository;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import com.example.aquafarm.Weather.Repository.WeatherInfoRepository;
import com.example.aquafarm.Weather.Response.ExternalApiException;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherInfoRepository weatherInfoRepository;
    private final AquafarmInfoRepository aquafarmInfoRepository;
    private final ExternalApiService externalApiService;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    public WeatherServiceImpl(WeatherInfoRepository weatherInfoRepository, AquafarmInfoRepository aquafarmInfoRepository, ExternalApiService externalApiService) {
        this.weatherInfoRepository = weatherInfoRepository;
        this.aquafarmInfoRepository = aquafarmInfoRepository;
        this.externalApiService = externalApiService;
    }

    @Override
    public void saveWeatherInfo(WeatherInfo weatherInfo) {
        weatherInfoRepository.save(weatherInfo);
    }

    @Override
    public void updateWeatherInfo(WeatherInfo weatherInfo) {
        weatherInfoRepository.save(weatherInfo);
    }

    @Override
    public WeatherInfo getWeatherInfoByAquafarmId(int aquafarmId) {
        Optional<WeatherInfo> weatherInfoOptional = weatherInfoRepository.findByAquafarmId(aquafarmId);
        return weatherInfoOptional.orElse(null);
    }

    @Override
    public WeatherInfo getWeatherInfoByAquafarm(AquafarmInfo aquafarmInfo) {
        return getWeatherInfoByAquafarmId(aquafarmInfo.getAquafarmId());
    }

    @Override
    public void updateWeatherInfoByAquafarmId(int aquafarmId) {
        AquafarmInfo aquafarmInfo = aquafarmInfoRepository.findById(aquafarmId).orElse(null);
        if (aquafarmInfo == null) {
            throw new IllegalArgumentException("Aquafarm not found with ID: " + aquafarmId);
        }

        WeatherInfo weatherInfo = getWeatherInfoByAquafarmId(aquafarmId);
        if (weatherInfo == null) {
            weatherInfo = new WeatherInfo();
        }

        LatLng coordinates = getCoordinates(aquafarmInfo.getAddress());
        if (coordinates != null) {
            double latitude = coordinates.lat;
            double longitude = coordinates.lng;

            weatherInfo.setLocationX(latitude);  // 수정: locationX 설정
            weatherInfo.setLocationY(longitude);  // 수정: locationY 설정

            String sunriseTime = externalApiService.getSunriseTimeFromWeatherAPI(latitude, longitude);
            if (sunriseTime != null) {
                weatherInfo.setSunrise(sunriseTime);
            }

            String sunsetTime = externalApiService.getSunsetTimeFromWeatherAPI(latitude, longitude);
            if (sunsetTime != null) {
                weatherInfo.setSunset(sunsetTime);
            }

            saveWeatherInfo(weatherInfo);
        }
    }
/*
    private LatLng getCoordinates(String address) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(googleApiKey).build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results.length > 0) {
                return results[0].geometry.location;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    @Override
    public String getSunriseTimeFromWeatherAPI(double latitude, double longitude) {
        String url = buildWeatherApiUrl(latitude, longitude);

        try {
            ResponseEntity<WeatherResponse> responseEntity = externalApiService.getApiResponse(url, WeatherResponse.class);
            WeatherResponse weatherResponse = responseEntity.getBody();
            if (weatherResponse != null && weatherResponse.getResponse() != null && weatherResponse.getResponse().getBody() != null) {
                return weatherResponse.getResponse().getBody().getItem().getSunrise();
            }
        } catch (ExternalApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getSunsetTimeFromWeatherAPI(double latitude, double longitude) {
        String url = buildWeatherApiUrl(latitude, longitude);

        try {
            ResponseEntity<WeatherResponse> responseEntity = externalApiService.getApiResponse(url, WeatherResponse.class);
            WeatherResponse weatherResponse = responseEntity.getBody();
            if (weatherResponse != null && weatherResponse.getResponse() != null && weatherResponse.getResponse().getBody() != null) {
                return weatherResponse.getResponse().getBody().getItem().getSunset();
            }
        } catch (ExternalApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildWeatherApiUrl(double latitude, double longitude) {
        String baseDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String apiUrl = weatherApiUrl
                .replace("{baseDateTime}", baseDateTime)
                .replace("{latitude}", String.valueOf(latitude))
                .replace("{longitude}", String.valueOf(longitude))
                .replace("{apiKey}", weatherApiKey);

        return UriComponentsBuilder.fromUriString(apiUrl)
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }

    @Override
    public void updateWeatherInfo() {
        List<AquafarmInfo> aquafarmInfoList = aquafarmInfoRepository.findAll();

        for (AquafarmInfo aquafarmInfo : aquafarmInfoList) {
            int aquafarmId = aquafarmInfo.getAquafarmId();

            WeatherInfo weatherInfo = weatherInfoRepository.findByAquafarmId(aquafarmId).orElse(null);
            if (weatherInfo == null) {
                weatherInfo = new WeatherInfo();
            }

            LatLng coordinates = getCoordinates(aquafarmInfo.getAddress());
            if (coordinates != null) {
                double latitude = coordinates.lat;
                double longitude = coordinates.lng;

                weatherInfo.setLocationX(latitude);
                weatherInfo.setLocationY(longitude);

                String sunriseTime = externalApiService.getSunriseTimeFromWeatherAPI(latitude, longitude);
                if (sunriseTime != null) {
                    weatherInfo.setSunrise(sunriseTime);
                }

                String sunsetTime = externalApiService.getSunsetTimeFromWeatherAPI(latitude, longitude);
                if (sunsetTime != null) {
                    weatherInfo.setSunset(sunsetTime);
                }

                weatherInfoRepository.save(weatherInfo);
            }
        }
    }

    // 좌표 변환을 위한 메서드
    private LatLng getCoordinates(String address) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAOEG0LCEJDSEXeLVIC50242dBcu6fQOF0").build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results.length > 0) {
                return results[0].geometry.location;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
