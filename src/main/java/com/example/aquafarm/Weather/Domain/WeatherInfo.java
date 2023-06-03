package com.example.aquafarm.Weather.Domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Weather_info")
public class WeatherInfo {
    @Id
    @Column(name = "weather_ID")
    private int weatherId;

    @Column(name = "locationX")
    private double locationX;

    @Column(name = "locationY")
    private double locationY;

    private Timestamp time;

    private LocalDateTime sunrise;

    private LocalDateTime sunset;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}