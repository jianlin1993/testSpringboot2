package com.murong.wjl.testng.test;


import com.murong.wjl.testng.TestNGBaseTest;
import com.murong.wjl.testspringboot2.Controller.billController;
import com.murong.wjl.testspringboot2.domain.Bill;
import com.murong.wjl.testspringboot2.mapper.BillMapper;
import com.murong.wjl.testspringboot2.utils.StrUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@PrepareForTest({StrUtil.class,PowerMockTest.class})
public class PowerMockTest extends PowerMockTestCase {

    private Logger logger= LoggerFactory.getLogger(PowerMockTest.class);
    @Mock
    BillMapper billMapper;

    @InjectMocks
    billController billController1;

    @BeforeClass
    public  void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(StrUtil.class);
    }

    /**
     * testNG整合mock测试
     */
    @Test
    public  void test01(){
        Bill bill=new Bill();
        bill.setRemark("testNG");
        PowerMockito.when(StrUtil.getJrnNo()).thenReturn("0000000001");
        //Mockito.when(billMapper.getBill(Mockito.anyString())).thenReturn(bill);
        Bill billRsp=billController1.getUser("000");
        logger.info(billRsp.getNo()+"");
        logger.info(billRsp.getRemark());
    }

    /**
     * mock静态方法
     */
    @Test
    public  void test02(){
        PowerMockito.when(StrUtil.getJrnNo()).thenReturn("0000000001");
        String no=billController1.getNo();
        logger.info(no);
    }


    /**
     * mock静态方法返回  想再从其流程中获取实际值   ---未实现
     */
    @Test
    public  void test03(){
        PowerMockito.when(StrUtil.getJrnNo()).thenReturn("0000000001");
        Bill billRsp=billController1.getBillByNo();
        logger.info(billRsp.getNo()+"");
        logger.info(billRsp.getRemark());
    }

}
