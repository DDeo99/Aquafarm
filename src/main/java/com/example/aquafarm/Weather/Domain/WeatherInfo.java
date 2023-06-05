package com.example.aquafarm.Weather.Domain;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
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
@Table(name = "weather_info")
public class WeatherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private int weatherId;

    @Column(name = "address")
    private String address;

    @Column(name = "locationX",nullable = true)
    private String locationX;

    @Column(name = "locationY",nullable = true)
    private String locationY;

    @Column(nullable = true)
    private LocalDateTime time;
    @Column(nullable = true)
    private String  sunrise;
    @Column(nullable = true)
    private String  sunset;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
