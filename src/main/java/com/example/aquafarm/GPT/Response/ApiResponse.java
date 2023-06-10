package com.example.aquafarm.GPT.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class ApiResponse {

    public static class SunRiseSetResponse {
        public String sunrise;
        public String sunset;
    }

    public static class FeedRecordItem {
        public String date;
        public int count;
    }

    public static class FeedRecordResponse {
        public List<FeedRecordItem> item;
    }

    public static class WaterInfoItem {
        public int waterId;
        public double waterTemp;
        public double doValue;
        public double turbi;
        public double nh4;
        public String date;
        public double ph;
    }

    public static class WaterInfoResponse {
        public List<WaterInfoItem> item;
    }

    public static class GPTService {

        private RestTemplate restTemplate = new RestTemplate();
        private ObjectMapper objectMapper = new ObjectMapper();

        //...

        public SunRiseSetResponse getSunriseSunset() {
            String url = "http://localhost:8080/weather/sunrise-sunset";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            try {
                return objectMapper.readValue(response.getBody(), SunRiseSetResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }

        public FeedRecordResponse getFoodRecord() {
            String url = "http://localhost:8080/fish-info/food-record";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            try {
                return objectMapper.readValue(response.getBody(), FeedRecordResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }

        public WaterInfoResponse getWaterInfo(String id) {
            String url = "http://localhost:8080/waterinfo/yearly/" + id;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            try {
                return objectMapper.readValue(response.getBody(), WaterInfoResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String prepareMessage(SunRiseSetResponse sunriseSunset, FeedRecordResponse foodRecord, WaterInfoResponse waterInfo) {
            // Here you can modify how the message should be prepared based on your needs
            // For example:
            return "Here is the sunrise and sunset info for today: sunrise at " + sunriseSunset.sunrise + ", sunset at " + sunriseSunset.sunset + "\n"
                    + "Here is the water info for the year: " + waterInfo.item.toString() + "\n"
                    + "Here is the feeding record: " + foodRecord.item.toString() + "\n";
        }
    }
}
