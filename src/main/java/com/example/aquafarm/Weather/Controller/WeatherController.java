package com.example.aquafarm.Weather.Controller;
import com.example.aquafarm.Weather.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
@Controller
public class WeatherController {

        // application.properties 파일에서 API 키 값을 가져옴
        @Value("${api.key}")
        private String apiKey;

        @GetMapping("/weather")
        public String getWeatherInfo(Model model) {
        // API 요청 URL 구성
        String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
        String locDate = "20230603"; // 조회할 날짜
            // Weather_info 테이블에서 locationX와 locationY 조회 (예시로 1번 데이터로 가정)
            double locationX = 1.23; // 예시 값, 실제로는 데이터베이스에서 조회해야 합니다.
            double locationY = 4.56; // 예시 값, 실제로는 데이터베이스에서 조회해야 합니다.

            String location = locationX + "," + locationY; // 조회할 지역 (위도, 경도 형식)

            String requestUrl = apiUrl + "?serviceKey=" + apiKey + "&locdate=" + locDate + "&location=" + location;

        // API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(requestUrl, WeatherResponse.class);
        WeatherResponse weatherResponse = response.getBody();

        if (weatherResponse != null && weatherResponse.getResponse() != null
                && weatherResponse.getResponse().getBody() != null) {
            // 응답 데이터에서 일출시간과 일몰시간 추출
            WeatherResponse.Body body = weatherResponse.getResponse().getBody();
            String sunriseTime = body.getItems().getItem().getSunrise();
            String sunsetTime = body.getItems().getItem().getSunset();

            // 모델에 데이터 전달
            model.addAttribute("sunriseTime", sunriseTime);
            model.addAttribute("sunsetTime", sunsetTime);
        }

        // weather.html 파일을 렌더링하여 응답 반환
        return "weather";
    }
}
