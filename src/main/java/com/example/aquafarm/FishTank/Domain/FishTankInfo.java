package com.example.aquafarm.FishTank.Domain;

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

    @Column(name = "aquafarm_ID")
    private int aquafarmId;

    @Column(name = "water_ID")
    private int waterId;

    @Column(name = "fish_ID")
    private int fishId;

    @Column(name = "weather_ID")
    private int weatherId;

    @Column(name = "tank_number")
    private int tankNumber;
}