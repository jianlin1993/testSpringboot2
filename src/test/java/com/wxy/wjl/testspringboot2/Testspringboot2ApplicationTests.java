package com.wxy.wjl.testspringboot2;

import com.alibaba.fastjson.JSON;
import com.googlecode.aviator.AviatorEvaluator;
import com.wxy.wjl.testspringboot2.config.MslSystemDsMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Testspringboot2ApplicationTests {



    @Test
    public void contextLoads() {
    }


    @Test
    public void testAviatorEvaluator(){
        System.out.println(AviatorEvaluator.execute("* == a2"));
    }


    @Autowired
    MslSystemDsMap mslSystemDsMap;

    @Test
    public void test(){
        // 获取所有系统名称list
        List<String> keyList = mslSystemDsMap.getMap().keySet().stream()
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(keyList));
        System.out.println(JSON.toJSONString(mslSystemDsMap.getMap()));
    }

}

