package com.wxy.wjl.testspringboot2;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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



}

