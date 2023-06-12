package com.example.cloud.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
@Async
public class SyncService {

    RestTemplate restTemplate = new RestTemplate();

    public Future<String> method1(Map<String, ArrayList<List<String>>> rawMap, String uuid) throws InterruptedException {
        LinkedMultiValueMap<String, String> newmap = new LinkedMultiValueMap<>();
        newmap.add("data",  JSONObject.toJSONString(rawMap));

        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(newmap, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://127.0.0.1:8080/api",httpEntity,String.class
        );
        String result = (String) JSONObject.parseObject(apiResponse.getBody()).get("res");
        if(result.equals("1"))
            return new AsyncResult<>(uuid);
        else
            return new AsyncResult<>("");
    }

    public Future<String> method2(Map<String, ArrayList<ArrayList<BigInteger>>> rawMap, String uuid) throws InterruptedException {
        LinkedMultiValueMap<String, String> newmap = new LinkedMultiValueMap<>();
        newmap.add("data",  JSONObject.toJSONString(rawMap));

        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(newmap, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://127.0.0.1:8080/placeapi",httpEntity,String.class
        );
        String result = (String) JSONObject.parseObject(apiResponse.getBody()).get("res");
        if(result.equals("1"))
            return new AsyncResult<>(uuid);
        else
            return new AsyncResult<>("");
    }
}
