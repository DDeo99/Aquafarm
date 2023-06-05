package com.example.aquafarm.Weather.Domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_ID")
    private long weatherId;

    @Column(name = "locationX")
    private double locationX;

    @Column(name = "locationY")
    private double locationY;

    private LocalDate time;

    private String  sunrise;

    private String  sunset;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}