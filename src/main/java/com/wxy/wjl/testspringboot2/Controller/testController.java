package com.wxy.wjl.testspringboot2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class testController {

    @ResponseBody
    @RequestMapping("job")
    public String test() throws Exception{
        System.out.println("进来了");
        Thread.sleep(30000);
        System.out.println("返回了");
        return "{\"result\":\"success\"}";
    }
}
