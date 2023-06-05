package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.DTO.GeolocationDTO;
import com.example.aquafarm.Weather.DTO.GeocodingResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Service
public class GeocodingService {

    private static final String GOOGLE_GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyAOEG0LCEJDSEXeLVIC50242dBcu6fQOF0"; // Google Geocoding API 키

    public GeolocationDTO getGeolocationFromAddress(String address) {
        // API 요청을 위한 RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 URL 생성
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_GEOCODING_API_URL)
                .queryParam("address", address)
                .queryParam("key", API_KEY);

        String apiUrl = builder.toUriString();
        System.out.println("API 요청 URL: " + apiUrl);

        // API 호출 및 응답 데이터 수신
        GeocodingResponse response = restTemplate.getForObject(builder.toUriString(), GeocodingResponse.class);

        // 응답 데이터에서 위도와 경도 추출
        if (response != null && response.getResults() != null && response.getResults().length > 0) {
            GeolocationDTO location = new GeolocationDTO();
            location.setLat(response.getResults()[0].getGeometry().getLocation().getLat());
            location.setLng(response.getResults()[0].getGeometry().getLocation().getLng());
            return location;
        }
        return null;
    }


}
