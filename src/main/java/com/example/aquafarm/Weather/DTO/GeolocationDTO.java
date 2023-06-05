package com.example.aquafarm.Weather.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeolocationDTO {
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lng")
    private double lng;

}
