package com.example.aquafarm.Fish.Controller;

import com.example.aquafarm.Fish.DTO.FishInfoDTO;
import com.example.aquafarm.Fish.Domain.FishInfo;
import com.example.aquafarm.Fish.Service.FishInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fish-info")
public class FishInfoController {

    private final FishInfoService fishInfoService;

    @Autowired
    public FishInfoController(FishInfoService fishInfoService) {
        this.fishInfoService = fishInfoService;
    }

    @GetMapping("/food-record")
    public List<FishInfoDTO> getFoodRecordCountWithin8Days() {
        return fishInfoService.getFoodRecordCountWithin8Days();
    }
}