package com.example.aquafarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getLCRiseSetInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=9tfrUPQ2c78KRdrN7%2F3cCiHrqC8Oe%2FoB3GhGUeiYM2FFoJDrV9%2FR3KG9rBVgPXEQAXdSuQPQgeRAhzjFbF0RRA%3D%3D"); /*서비스 키*/
        urlBuilder.append("&" + URLEncoder.encode("locdate", "UTF-8") + "=" + URLEncoder.encode("20150101", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode("12800", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode("3613", "UTF-8")); /*지역*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("응답 코드: " + conn.getResponseCode());
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
        }
    }
}