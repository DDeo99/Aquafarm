package com.example.aquafarm.GPT.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SunriseSunsetData {
    @JsonProperty("sunrise")
    private String sunriseTime;
    @JsonProperty("sunset")
    private String sunsetTime;

    public LocalDateTime getSunriseDateTime() {
        // sunrise 값을 "HHmm" 형식으로 변환하여 LocalDateTime으로 반환
        LocalTime localTime = LocalTime.parse(sunriseTime, DateTimeFormatter.ofPattern("HHmm"));
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    public LocalDateTime getSunsetDateTime() {
        // sunset 값을 "HHmm" 형식으로 변환하여 LocalDateTime으로 반환
        LocalTime localTime = LocalTime.parse(sunsetTime, DateTimeFormatter.ofPattern("HHmm"));
        return LocalDateTime.of(LocalDate.now(), localTime);
    }
}