package com.wxy.wjl.testng.test;

import com.wxy.wjl.testspringboot2.Controller.billController;
import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MockTest {

    Logger logger= LoggerFactory.getLogger(MockTest.class);

    @Mock
    BillMapper billMapper;

    @InjectMocks
    billController billController1;

    @BeforeClass
    public  void before(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testNG整合mock测试
     */
    @Test
    public  void test01(){
        Bill bill=new Bill();
        bill.setRemark("testNG");
        Mockito.when(billMapper.getBill(Mockito.anyString())).thenReturn(bill);
        Bill billRsp=billController1.getUser("0000000001");
        logger.debug(billRsp.getNo()+" ");
        logger.info(billRsp.getRemark());
        logger.error(billRsp.getTxTyp());

    }

}
