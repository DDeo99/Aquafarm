package com.example.aquafarm.Fish.Domain;

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
@Table(name = "fish_info")
public class FishInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fish_ID")
    private int fishId;

    private String species;

    private byte[] photo;

    private double size;

    private double growth;

    @Column(name = "food_record")
    private LocalDateTime foodRecord;

    @Column(name = "stocking_date")
    private int stockingDate;

    @Column(name = "feeding_amount")
    private double feedingAmount;

    private Timestamp date;
}