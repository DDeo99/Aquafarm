package com.example.aquafarm.Aquafarm.Domain;

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
    @Column(name = "aquafarm_ID")
    private int aquafarmId;

    private String address;

    @Column(name = "user_ID")
    private int userId;
}