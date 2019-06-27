package com.murong.wjl.testspringboot2.Controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class helloController {

    @RequestMapping("/hello")
    private String index() {
        return "Hello World!";
    }
}


