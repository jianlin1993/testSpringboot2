package com.wxy.wjl.testspringboot2.kafka;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("kafka")
public class WjlKafkaController {

    @RequestMapping("/send/{str}")
    @ResponseBody
    public String send(@PathVariable String str) throws Exception{
        WjlKafkaProducer wjlKafkaProducer=new WjlKafkaProducer();
        wjlKafkaProducer.containerInit();
        wjlKafkaProducer.syncSend("test",str);


        return "success";
    }

}
