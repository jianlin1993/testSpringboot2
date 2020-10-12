package com.wxy.wjl.testspringboot2.cloud.restTemplete;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.cloud.entity.RestTempleteRspBO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WjlRestTemplete {

    public static String testRestTemplete(){
        //请求地址
        String url = "http://localhost:8082/provider/testRestTemplete";

        RestTemplate restTemplate = new RestTemplate();
        RestTempleteRspBO restTempleteRspBO = restTemplate.postForObject(url, "RestTemplete", RestTempleteRspBO.class);
        return JSON.toJSONString(restTempleteRspBO);
    }

    public static void main(String[] args) {
        System.out.println(testRestTemplete());
    }



}
