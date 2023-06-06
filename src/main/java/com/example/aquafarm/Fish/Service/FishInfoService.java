package com.example.aquafarm.Fish.Service;

import com.example.aquafarm.Fish.DTO.FishInfoDTO;
import com.example.aquafarm.Fish.Domain.FishInfo;
import com.example.aquafarm.Fish.Repository.FishInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FishInfoService {

    private final FishInfoRepository fishInfoRepository;

    @Autowired
    public FishInfoService(FishInfoRepository fishInfoRepository) {
        this.fishInfoRepository = fishInfoRepository;
    }


    public List<FishInfoDTO> getFoodRecordCountWithin8Days() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime eightDaysAgo = currentDate.minusDays(8);

        List<FishInfo> fishInfoList = fishInfoRepository.findByFoodRecordBetween(eightDaysAgo, currentDate);
        Map<LocalDate, Integer> foodRecordCountMap = new HashMap<>();

        for (FishInfo fishInfo : fishInfoList) {
            LocalDate date = fishInfo.getFoodRecord().toLocalDate();
            foodRecordCountMap.put(date, foodRecordCountMap.getOrDefault(date, 0) + 1);
        }

        List<FishInfoDTO> foodRecordDtoList = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : foodRecordCountMap.entrySet()) {
            LocalDate date = entry.getKey();
            int feedingCount = entry.getValue();
            FishInfoDTO fishInfoDto = new FishInfoDTO(date, feedingCount);
            foodRecordDtoList.add(fishInfoDto);
        }

        return foodRecordDtoList;
    }
}