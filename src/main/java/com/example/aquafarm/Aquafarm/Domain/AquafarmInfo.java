package com.example.aquafarm.Aquafarm.Domain;

import com.example.aquafarm.FishTank.Domain.FishTankInfo;
import com.example.aquafarm.User.Domain.UserInfo;
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
@Table(name = "aquafarm_info")
public class AquafarmInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aquafarm_ID")
    private int aquafarmId;

    private String address;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private UserInfo userInfo;

}