package com.example.aquafarm.Weather.Service;

import com.example.aquafarm.Weather.DTO.WeatherDTO;
import com.example.aquafarm.Weather.Response.ExternalApiException;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    public ExternalApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> ResponseEntity<T> getApiResponse(String apiUrl, Class<T> responseType) {
        return restTemplate.getForEntity(apiUrl, responseType);
    }

    @Override
    public WeatherResponse getWeatherData(String apiUrl) {
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());

        ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(apiUrl, WeatherResponse.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ExternalApiException("Failed to retrieve weather data from API");
        }
    }

    @Override
    public String getSunriseTimeFromWeatherAPI(double latitude, double longitude) {
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String serviceKey = "9tfrUPQ2c78KRdrN7%2F3cCiHrqC8Oe%2FoB3GhGUeiYM2FFoJDrV9%2FR3KG9rBVgPXEQAXdSuQPQgeRAhzjFbF0RRA%3D%3D";
        String baseDate = "20230604";
        String baseTime = "0600";
        String nx = "55";
        String ny = "127";
        String numOfRows = "10";
        String pageNo = "1";

        String apiUrlWithParams = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        WeatherResponse weatherResponse = getWeatherData(apiUrlWithParams);

        if (weatherResponse != null && "success".equals(weatherResponse.getStatus())) {
            com.example.aquafarm.Weather.Response.Item item = weatherResponse.getResponse().getBody().getItem();
            if (item != null) {
                String sunriseTime = item.getSunrise();
                return sunriseTime;
            }
        }

        throw new ExternalApiException("Failed to retrieve sunrise time from Weather API");
    }

    @Override
    public String getSunsetTimeFromWeatherAPI(double latitude, double longitude) {
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String serviceKey = "9tfrUPQ2c78KRdrN7%2F3cCiHrqC8Oe%2FoB3GhGUeiYM2FFoJDrV9%2FR3KG9rBVgPXEQAXdSuQPQgeRAhzjFbF0RRA%3D%3D";
        String baseDate = "20230604";
        String baseTime = "0600";
        String nx = "55";
        String ny = "127";
        String numOfRows = "10";
        String pageNo = "1";

        String apiUrlWithParams = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        WeatherResponse weatherResponse = getWeatherData(apiUrlWithParams);

        if (weatherResponse != null && "success".equals(weatherResponse.getStatus())) {
            com.example.aquafarm.Weather.Response.Item item = weatherResponse.getResponse().getBody().getItem();
            if (item != null) {
                String sunsetTime = item.getSunset();
                return sunsetTime;
            }
        }

        throw new ExternalApiException("Failed to retrieve sunset time from Weather API");
    }

    @Override
    public WeatherDTO getWeatherDTO(String location) {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(weatherApiUrl)
                .path("/weather")
                .queryParam("location", location)
                .queryParam("key", weatherApiKey)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        WeatherResponse weatherResponse = getWeatherData(apiUrl);

        if (Objects.requireNonNull(weatherResponse).getStatus().equals("success")) {
            double latitude = weatherResponse.getResponse().getBody().getItem().getLatitude();
            double longitude = weatherResponse.getResponse().getBody().getItem().getLongitude();

            String sunriseTime = getSunriseTimeFromWeatherAPI(latitude, longitude);
            String sunsetTime = getSunsetTimeFromWeatherAPI(latitude, longitude);

            return WeatherDTO.builder()
                    .sunrise(sunriseTime)
                    .sunset(sunsetTime)
                    .build();
        } else {
            throw new ExternalApiException("Failed to retrieve weather data from API");
        }
    }
}
