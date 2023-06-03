package com.example.aquafarm.Weather.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDTO {
    @JsonProperty("sunrise")
    private String sunrise;

    @JsonProperty("sunset")
    private String sunset;

    // 생성자, getter, setter 등 필요한 코드를 추가해주세요
}