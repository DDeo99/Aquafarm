package com.example.aquafarm.Weather.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiseSetInfoDTO {
    private String resultCode;
    private String resultMsg;
    private String aste;
    private String astm;
    private String civile;
    private String civilm;
    private String latitude;
    private String latitudeNum;
    private String location;
    private String locdate;
    private String longitude;
    private String longitudeNum;
    private String moonrise;
    private String moonset;
    private String moontransit;
    private String naute;
    private String nautm;
    private String sunrise;
    private String sunset;
    private String suntransit;
}
