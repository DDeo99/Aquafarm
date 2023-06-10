package com.example.aquafarm.GPT.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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"; // 본인의 API 키를 사용하세요.

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GPTService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String getWaterInfo(String id) {
        String url = "http://localhost:8080/waterinfo/yearly/" + id;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
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

    public String askGPT(String question) {
        try {
            String waterInfo = getWaterInfo("1"); // replace "your_id" with the actual ID
            String foodRecord = getFoodRecord();
            String sunriseSunset = getSunriseSunset();
            String preMessage = prepareMessage(waterInfo, foodRecord, sunriseSunset);

            // Logging the data before sending the question
            System.out.println("Prepared message: " + preMessage);
            System.out.println("Question: " + question);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + API_KEY);

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", question);
            messages.add(message);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 50);
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