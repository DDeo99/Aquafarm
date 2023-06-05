package com.example.aquafarm.GPT.Domain;

import com.example.aquafarm.Fish.Domain.FishInfo;
import com.example.aquafarm.Water.Domain.WaterInfo;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import lombok.*;

import javax.persistence.*;
import java.security.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GPT_Q")
public class GPTQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QPTQ_ID")
    private int qptqId;

    @Column(name = "Question_Date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate questionDate;

    @Column(name = "Question", columnDefinition = "TEXT")
    private String question;

    @ManyToOne
    @JoinColumn(name = "fish_ID")
    private FishInfo fishInfo;

    @ManyToOne
    @JoinColumn(name = "water_ID")
    private WaterInfo waterInfo;

    @ManyToOne
    @JoinColumn(name = "weather_ID")
    private WeatherInfo weatherInfo;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}