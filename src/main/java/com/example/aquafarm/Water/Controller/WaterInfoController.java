package com.example.aquafarm.Water.Controller;

import com.example.aquafarm.Water.DTO.WaterDTO;
import com.example.aquafarm.Water.Domain.WaterInfo;
import com.example.aquafarm.Water.Repository.WaterInfoRepository;
import com.example.aquafarm.Water.Service.KibanaAPIService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/waterinfo")
public class WaterInfoController {

    private final WaterInfoRepository waterInfoRepository;
    private final KibanaAPIService kibanaAPIService;

    @Autowired
    public WaterInfoController(WaterInfoRepository waterInfoRepository, KibanaAPIService kibanaAPIService) {
        this.waterInfoRepository = waterInfoRepository;
        this.kibanaAPIService = kibanaAPIService;
    }

    @GetMapping("/data")
    public String getDataFromKibana() {
        String kibanaUrl = "http://your-kibana-url.com/api/console/proxy?path=_search";
        String result = null;

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(kibanaUrl);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/dashboard-data")
    public String getDashboardData() {
        String dashboardId = "d5acbe30-0440-11ee-b295-c966d495b73d";
        String dashboardData = kibanaAPIService.getDashboardData(dashboardId);
        return dashboardData;
    }

    // 그래프 데이터를 JSON 형식으로 변환하여 클라이언트에 전송하는 메소드
    @GetMapping("/graph")
    public ResponseEntity<Map<String, Object>> getGraphData() {
        // 대시보드 ID 설정
        String dashboardId = "d5acbe30-0440-11ee-b295-c966d495b73d";

        // 대시보드 데이터 가져오기
        String dashboardData = kibanaAPIService.getDashboardData(dashboardId);

        // 데이터를 가공하여 그래프 데이터셋 생성
        DefaultCategoryDataset dataset = createDataset(dashboardData);

        // 데이터셋을 JSON 형식으로 변환
        String jsonData = convertDatasetToJson(dataset);

        // JSON 데이터를 Map에 담아 전송
        Map<String, Object> response = new HashMap<>();
        response.put("data", jsonData);
        return ResponseEntity.ok(response);
    }
    private DefaultCategoryDataset createDataset(String dashboardData) {
        // 대시보드 데이터를 가공하여 그래프 데이터셋을 생성합니다.
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // 데이터 가공 로직 추가
        // ...

        return dataset;
    }

    private JFreeChart createLineChart(DefaultCategoryDataset dataset) {
        // 라인 차트 생성
        JFreeChart chart = ChartFactory.createLineChart(
                "Graph Example",   // 차트 제목
                "Date",            // X 축 레이블
                "Value",           // Y 축 레이블
                dataset,           // 데이터셋
                PlotOrientation.VERTICAL,
                true,              // 범례 표시 여부
                true,              // 도구 팁 표시 여부
                false              // URL 링크 표시 여부
        );

        return chart;
    }

    private String convertDatasetToJson(DefaultCategoryDataset dataset) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(dataset);
            return jsonData;
        } catch (Exception e) {
            // 변환 실패 시 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/yearly/{id}")
    public List<WaterDTO> getYearlyData(@PathVariable("id") int id) {
        // 현재로부터 1년 전
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Timestamp start = new Timestamp(calendar.getTime().getTime());

        // 현재 시간
        Timestamp end = new Timestamp(System.currentTimeMillis());

        // 데이터베이스에서 1년간의 데이터를 가져와 WaterDTO 객체로 변환하여 반환
        return waterInfoRepository.findAllByDateBetween(id, start, end).stream()
                .map(waterInfo -> new WaterDTO(waterInfo.getWaterId(), waterInfo.getWaterTemp(), waterInfo.getDoValue(), waterInfo.getTurbi(), waterInfo.getNh4(), waterInfo.getPH(), waterInfo.getDate()))
                .collect(Collectors.toList());

    }
}