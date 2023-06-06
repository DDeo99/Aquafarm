package com.example.aquafarm.Weather.Controller;


import com.example.aquafarm.Weather.DTO.RiseSetInfoDTO;
import com.example.aquafarm.Weather.Domain.WeatherInfo;
import com.example.aquafarm.Weather.Repository.WeatherInfoRepository;
import com.example.aquafarm.Weather.Service.RiseSetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
public class RiseSetInfoController {
    private final RiseSetInfoService riseSetInfoService;
    private final WeatherInfoRepository weatherInfoRepository;

    @Autowired
    public RiseSetInfoController(RiseSetInfoService riseSetInfoService,WeatherInfoRepository weatherInfoRepository) {
        this.riseSetInfoService = riseSetInfoService;
        this.weatherInfoRepository = weatherInfoRepository;
    }

    @GetMapping("/rise-set-info")
    public ResponseEntity<RiseSetInfoDTO> getRiseSetInfo(
            @RequestParam("address") String address
    ) {
        try {
            WeatherInfo weatherInfo = weatherInfoRepository.findByAddress(address);
            if (weatherInfo == null) {
                throw new IllegalArgumentException("Invalid Address");
            }

            // 오늘의 날짜 가져오기
            LocalDate currentDate = LocalDate.now();

            // WeatherInfo 테이블에서 오늘의 날짜와 같은 날짜의 데이터 가져오기
            WeatherInfo todayWeatherInfo = weatherInfoRepository.findByAddressAndTime(address, currentDate);

            // 데이터가 없는 경우, 새로운 데이터 생성
            if (todayWeatherInfo == null) {
                todayWeatherInfo = WeatherInfo.builder()
                        .address(address)
                        .time(currentDate)
                        .build();
            }

            RiseSetInfoDTO response = riseSetInfoService.getRiseSetInfo(weatherInfo.getAddress());

            // 필요한 정보 업데이트
            riseSetInfoService.updateWeatherInfo(weatherInfo, response);

            // 업데이트된 WeatherInfo 저장
            weatherInfoRepository.save(weatherInfo);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}