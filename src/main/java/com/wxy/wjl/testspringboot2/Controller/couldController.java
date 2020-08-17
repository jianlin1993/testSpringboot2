package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.providerapi.entiy.ProviderReqBO;
import com.wxy.wjl.providerapi.service.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class couldController {

    @Autowired
    ServiceProvider serviceProvider;


    @RequestMapping("/testCloud")
    public String testCloud(){
        ProviderReqBO providerReqBO=new ProviderReqBO();
        providerReqBO.setUsrNm("测试用户");
        return serviceProvider.testProvider(providerReqBO);
    }
}
