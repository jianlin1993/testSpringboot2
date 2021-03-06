package com.wxy.wjl.testng.test;

import com.wxy.wjl.testspringboot2.Controller.billController;
import com.wxy.wjl.testspringboot2.Testspringboot2Application;
import com.wxy.wjl.testspringboot2.domain.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.InputStream;


@SpringBootTest(classes = Testspringboot2Application.class)
public class UnitTest extends AbstractTestNGSpringContextTests{


    @Autowired
    billController billController1;

    /**
     * springboot使用testNG进行单元测试
     * 需要继承AbstractTestNGSpringContextTests
     * 并使用@SpringBootTest(classes =Application.class)  启动类
     */
    @Test
    public  void test01(){
        Bill bill=new Bill();
        bill.setRemark("testNG");
       Bill billRsp=billController1.getUser("0000000001");
       System.out.println(billRsp.getRemark());
    }
    @Test
    public  void test03(){
        Bill bill=billController1.getBillByNo();
        logger.info(bill.getNo()+"");
        logger.info(bill.getRemark());
    }
    @Test
    public  void test04(){
        String acStsw="00000000000000001000";
        System.out.println(acStsw.substring(16,17));
    }



}
