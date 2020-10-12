package com.wxy.wjl.testspringboot2.cloud.restTemplete;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WjlRestTempleteController {

    @Autowired
    WjlRestTemplete testRestTemplete;

    @ResponseBody
    @RequestMapping("testRestTemplete")
    public String testRestTemplete() throws Exception{

        return testRestTemplete.testRestTemplete();
    }


}
