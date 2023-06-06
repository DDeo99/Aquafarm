package com.example.aquafarm.Fish.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishInfoDTO {
    private LocalDate date;
    private int count;
}
