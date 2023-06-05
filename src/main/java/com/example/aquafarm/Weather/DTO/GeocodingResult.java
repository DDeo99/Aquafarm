package com.example.aquafarm.Weather.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeocodingResult {

    @JsonProperty("geometry")
    private GeocodingGeometry geometry;

    // 기타 필요한 필드 및 메서드 구현

    public GeocodingGeometry getGeometry() {
        return geometry;
    }


}