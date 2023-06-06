package com.example.aquafarm.Water.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class KibanaAPIService {

    public String getKibanaData() {
        String kibanaUrl = "http://localhost:5601/api/console/proxy?path=_search";
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


    public String getDashboardData(String dashboardId) {
        String kibanaUrl = "http://localhost:5601/api/saved_objects/_find?type=dashboard&fields=title&search=" + dashboardId;
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
}