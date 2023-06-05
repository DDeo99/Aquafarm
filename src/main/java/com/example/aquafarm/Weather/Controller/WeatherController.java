package com.example.aquafarm.Weather.Controller;
import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Aquafarm.Repository.AquafarmInfoRepository;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import com.example.aquafarm.Weather.Repository.WeatherInfoRepository;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import com.example.aquafarm.Weather.Service.ExternalApiService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Controller
public class WeatherController {

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Autowired
    private AquafarmInfoRepository aquafarmInfoRepository;

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/weather")
    public String getWeatherInfo(Model model) {
        // aquafarm_info 테이블의 첫 번째 데이터를 가져옴
        Optional<AquafarmInfo> aquafarmInfoOptional = aquafarmInfoRepository.findFirstByOrderByAquafarmId();
        AquafarmInfo aquafarmInfo = aquafarmInfoOptional.orElse(null);

        // 주소를 위도와 경도로 변환하여 가져옴
        String address = aquafarmInfo.getAddress();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAOEG0LCEJDSEXeLVIC50242dBcu6fQOF0")  // Geocoding API 키 입력
                .build();

        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        if (results.length > 0) {
            LatLng location = results[0].geometry.location;
            double latitude = location.lat;
            double longitude = location.lng;

            // 일출 시간과 일몰 시간을 가져옴
            String sunriseTime = externalApiService.getSunriseTimeFromWeatherAPI(latitude, longitude);
            String sunsetTime = externalApiService.getSunsetTimeFromWeatherAPI(latitude, longitude);

            // 모델에 데이터 전달
            model.addAttribute("sunriseTime", sunriseTime);
            model.addAttribute("sunsetTime", sunsetTime);

            return "weather";
        } else {
            // 주소를 변환할 수 없는 경우 처리
            System.out.println("Failed to convert address to latitude and longitude.");
            return "error";
        }
    }
}
