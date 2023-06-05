package com.example.aquafarm.Weather.DTO;

import com.example.aquafarm.Weather.DTO.GeocodingResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeocodingResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("results")
    private GeocodingResult[] results;
}
