package com.example.aquafarm.Fish.Domain;

import com.example.aquafarm.FishTank.Domain.FishTankInfo;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
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
    @Column(nullable = true)
    private byte[] photo;
    @Column(nullable = true)
    private double size;
    @Column(nullable = true)
    private double growth;

    @Column(name = "food_record",nullable = true)
    private LocalDateTime foodRecord;

    @Column(name = "stocking_date",nullable = true)
    private int stockingDate;

    @Column(name = "feeding_amount",nullable = true)
    private double feedingAmount;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}