package com.example.aquafarm.GPT.Service;

import com.example.aquafarm.GPT.DTO.SunriseSunsetData;
import com.example.aquafarm.Weather.Service.WeatherInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.data.json.impl.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GPTService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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"; // 본인의 API 키를 사용하세요.
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherInfoService weatherInfoService;

    @Autowired
    public GPTService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, WeatherInfoService weatherInfoService) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.weatherInfoService = weatherInfoService;
    }

    public String getWaterInfo(String id) {
        String url = "http://localhost:8080/waterinfo/yearly/" + id;
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String waterInfo = response.getBody();

                String waterInfoWithoutId = waterInfo.replace("<waterId>" + id + "</waterId>", "");
                return waterInfoWithoutId;
            } else {
                throw new RuntimeException("Failed to retrieve water info: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving water info: " + e.getMessage());
        }
    }

    public String getFoodRecord() {
        String url = "http://localhost:8080/fish-info/food-record";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public String getSunriseSunset() {
        String url = "http://localhost:8080/weather/sunrise-sunset";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public String prepareMessage(String waterInfo, String foodRecord, String sunriseSunset) {
        return "Here is the water info for the year: " + waterInfo + "\n"
                + "Here is the feeding record: " + foodRecord + "\n"
                + "Here is the sunrise and sunset info for today: " + sunriseSunset + "\n";
    }

    private SunriseSunsetData parseSunriseSunset(String sunriseSunset) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(sunriseSunset, SunriseSunsetData.class);
        } catch (IOException e) {
            throw new RuntimeException("일출 시간 및 일몰 시간 파싱 오류", e);
        }
    }

    public String askGPT(String question) {
        try {
            String waterInfo = getWaterInfo("1"); // replace "your_id" with the actual ID
            String foodRecord = getFoodRecord();
            String sunriseSunset = getSunriseSunset();
            String preMessage = prepareMessage(waterInfo, foodRecord, sunriseSunset);

            // Logging the data before sending the question
            System.out.println("Prepared message: " + preMessage);
            System.out.println("Question: " + question);

            String modifiedQuestion = question.toLowerCase();
            if (modifiedQuestion.contains("밥을 언제 줄까?")) {
                SunriseSunsetData sunriseSunsetData = parseSunriseSunset(sunriseSunset);
                if (sunriseSunsetData != null) {
                    LocalDateTime sunriseDateTime = sunriseSunsetData.getSunriseDateTime();
                    LocalDateTime sunsetDateTime = sunriseSunsetData.getSunsetDateTime();

                    // 밥 주는 시간 계산하기
                    LocalDateTime feedingTime1 = sunriseDateTime.minusMinutes(30);
                    LocalDateTime feedingTime2 = sunsetDateTime.minusMinutes(30);

                    // 밥 주는 시간을 문자열로 포맷팅
                    String formattedFeedingTime1 = feedingTime1.format(DateTimeFormatter.ofPattern("hh:mm a"));
                    String formattedFeedingTime2 = feedingTime2.format(DateTimeFormatter.ofPattern("hh:mm a"));

                    // 응답에 밥 주는 시간 추가하기
                    preMessage += "광어에게 밥을 줄 시간은 아침: " + formattedFeedingTime1 + ", 저녁: " + formattedFeedingTime2 + "입니다.";
                } else {
                    // 오류 처리 로직 추가
                    throw new RuntimeException("일출 시간 파싱 오류");
                }
            } else if (modifiedQuestion.contains("급이 기록")) {
                preMessage = "Here is the feeding record for your reference:\n" + foodRecord;
            } else if (modifiedQuestion.contains("provide the sunrise and sunset info")) {
                preMessage = "Here is the sunrise and sunset info for today:\n" + sunriseSunset;
            } else if (modifiedQuestion.contains("provide the water info")) {
                preMessage += "Here is the water information:\n" + waterInfo;

             } else if (modifiedQuestion.contains("수질 관리")) {
            preMessage += "수질에 문제가 없어보입니다.:\n" ;
             }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + API_KEY);

            List<Map<String, String>> messages = new ArrayList<>();

            // System message with preMessage
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", preMessage);
            messages.add(systemMessage);

            // User message with the question
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.add(userMessage);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 500);
            requestBody.put("model", "gpt-3.5-turbo");

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response: " + response.getBody());
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
                if (responseBody.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (choices != null && !choices.isEmpty() && choices.get(0).containsKey("message")) {
                        Map<String, Object> messageResponse = (Map<String, Object>) choices.get(0).get("message");
                        if (messageResponse.containsKey("content")) {
                            String answer = (String) messageResponse.get("content");
                            return answer.trim();
                        }
                    }
                }
            } else {
                throw new RuntimeException("ChatGPT API 요청 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("ChatGPT API 호출 중 오류 발생", e);
        }
        throw new RuntimeException("ChatGPT API 호출에서 예상치 못한 상황 발생");
    }
}