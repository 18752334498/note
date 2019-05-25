package com.yucong.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class RestTemplateTet {

    public static void main(String[] args) {
        post1();
    }

    // post请求，multipart/form-data格式，new HttpEntity的第一个参数只能是MultiValueMap，不能是HashMap||JSONObject
    // parameters.add中的value只能是HashMap||JSONObject，不能是String
    private static void post1() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("age", "33");
        parameters.add("_request", map);// 可以是map，可以是json对象，但是不能为string字符串
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parameters, headers);

        JSONObject object = restTemplate.postForObject("http://localhost:8080/login/validate", httpEntity, JSONObject.class);
        System.out.println(object.toJSONString());
    }

    // post请求，application/json格式，new HttpEntity的第一个参数只能是HashMap||JSONObject，不能是MultiValueMap
    private static void post2() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> json = new HashMap<>();
        json.put("username", "tom");
        json.put("age", "33");

        // HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parameters, headers); //fail
        // HttpEntity<JSONObject> httpEntity = new HttpEntity<>(json, headers); //success
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(json, headers); //success

        JSONObject object = restTemplate.postForObject("http://localhost:8080/login/validate2", httpEntity, JSONObject.class);
        System.out.println(object.toJSONString());
    }

}
