package com.example.aquafarm.Weather.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeocodingGeometry {
    @JsonProperty("location")
    private GeolocationDTO location;

    public GeolocationDTO getLocation() {
        return location;
    }
}
