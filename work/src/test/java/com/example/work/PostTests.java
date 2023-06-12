package com.example.work;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WorkApplication.class)
public class PostTests {

    public RestTemplate restTemplate = new RestTemplate();
    @Test
    public void post(){

        String uuids = "1,2,3";
        Map<String, String> map = new HashMap<>();
        map.put("role", "admin");
        map.put("uuid", uuids);
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://192.168.1.101:8081/idsearch",httpEntity,String.class
        );
        System.out.println((String) apiResponse.getBody());
    }
}
