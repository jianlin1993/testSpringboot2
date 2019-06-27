package com.murong.wjl.testng.test;


import com.murong.wjl.testspringboot2.Controller.billController;
import com.murong.wjl.testspringboot2.domain.Bill;
import com.murong.wjl.testspringboot2.mapper.BillMapper;
import com.murong.wjl.testspringboot2.utils.StringUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@PrepareForTest({StringUtil.class})
public class PowerMockTest {

    @Mock
    BillMapper billMapper;

    @InjectMocks
    billController billController1;

    @BeforeClass
    public  void before(){
        MockitoAnnotations.initMocks(this);
        //PowerMockito.mockStatic(StringUtil.class);
    }

    /**
     * testNG整合mock测试
     */
    @Test
    public  void test01(){
        Bill bill=new Bill();
        bill.setRemark("testNG");
        PowerMockito.when(StringUtil.getJrnNo()).thenReturn("0000000001");
        Mockito.when(billMapper.getBill(Mockito.anyString())).thenReturn(bill);
        Bill billRsp=billController1.getUser("000");
        System.out.println(billRsp.getRemark());
    }



}
