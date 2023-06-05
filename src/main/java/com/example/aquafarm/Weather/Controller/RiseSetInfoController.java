package com.example.aquafarm.Weather.Controller;


import com.example.aquafarm.Weather.DTO.RiseSetInfoDTO;
import com.example.aquafarm.Weather.Service.RiseSetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RiseSetInfoController {
    private final RiseSetInfoService riseSetInfoService;

    @Autowired
    public RiseSetInfoController(RiseSetInfoService riseSetInfoService) {
        this.riseSetInfoService = riseSetInfoService;
    }

    @GetMapping("/rise-set-info")
    public ResponseEntity<RiseSetInfoDTO> getRiseSetInfo(
            @RequestParam("weatherId") int weatherId
    ) {
        try {
            RiseSetInfoDTO response = riseSetInfoService.getRiseSetInfo(weatherId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}