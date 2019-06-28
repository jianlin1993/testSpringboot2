package com.wxy.wjl.testspringboot2.Controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class demo3Controller {
        @RequestMapping("/demo3")
        private String demo3() {
            System.out.println("进入控制器");
            return "3";
        }
}
