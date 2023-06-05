package com.example.aquafarm.Water.Domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "waterinfo")
public class WaterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_ID")
    private int waterId;

    @Column(name = "water_temp")
    private double waterTemp;

    @Column(name = "do")
    private double doValue;

    private double turbi;

    private double nh4;

    private double pH;

    private Timestamp date;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}