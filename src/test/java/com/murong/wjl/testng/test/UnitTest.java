package com.murong.wjl.testng.test;

import com.murong.wjl.testspringboot2.Controller.billController;
import com.murong.wjl.testspringboot2.Testspringboot2Application;
import com.murong.wjl.testspringboot2.domain.Bill;
import com.murong.wjl.testspringboot2.mapper.BillMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


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

}
