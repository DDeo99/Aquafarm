package com.example.aquafarm.Water.DTO;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterDTO {

    private int waterId;
    private double waterTemp;
    private double doValue;
    private double turbi;
    private double nh4;
    private double pH;
    private Timestamp date;
}