package com.example.aquafarm.FishTank.Domain;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Fish.Domain.FishInfo;
import com.example.aquafarm.Water.Domain.WaterInfo;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fish_tank_info")
public class FishTankInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fish_tank_ID")
    private int fishTankId;

    @ManyToOne
    @JoinColumn(name = "aquafarm_ID")
    private AquafarmInfo aquafarmInfo;

    @OneToOne
    @JoinColumn(name = "water_ID")
    private WaterInfo waterInfo;

    @ManyToOne
    @JoinColumn(name = "fish_ID")
    private FishInfo fishInfo;

    @ManyToOne
    @JoinColumn(name = "weather_ID")
    private WeatherInfo weatherInfo;

    @Column(name = "tank_number")
    private int tankNumber;
}