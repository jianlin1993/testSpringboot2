package com.murong.wjl.testng.test;

import com.murong.wjl.testng.TestNGBaseTest;
import com.murong.wjl.testng.dataprovider.BeanUtil;
import com.murong.wjl.testng.dbUtils.DbUtilsHelp;
import com.murong.wjl.testspringboot2.Controller.billController;
import com.murong.wjl.testspringboot2.domain.Bill;
import com.wjl.dubbo.test.service.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class test1 extends TestNGBaseTest {

    private billController billControllerBean= BeanUtil.getBean("billControllerBean");

    //private DubboService dubboService= BeanUtil.getBean("dubboService");

    DbUtilsHelp dbUtilsHelp=new DbUtilsHelp();

    @BeforeClass
    public void beforeClass(){
        dbUtilsHelp.setDataSource("classpath:/config/beansConfig.xml");
    }

    //db测试
    @Test
    public void testDB(){
        List<Map<String,Object>> list=dbUtilsHelp.selectList("select * from usr;");
        for(Map<String,Object> map : list){
            System.out.println(map.get("USR_NM"));
        }
    }

    //dubbo测试
//    @Test
//    public void testDubbo(){
//        String returnStr=dubboService.testDubbo("测试工程传输字符串");
//        System.out.println(returnStr);
//    }

    //excel测试
    @Test(dataProvider = "excelDataProvider")
    public void test01(Map<String ,String> testData) throws Exception{
        String num=testData.get("TEST_CASE_ID")+"    "+testData.get("name");
        System.out.println(num);
        billControllerBean.sout();
        //构建对象
        Bill bill=buildJavaBeanModel("Bill",Bill.class,testData,this);
        System.out.println("单对象输出："+bill.getRemark());

        //构建对象list
        List<Bill> billList=buildJavaBeanListModel("BillList",Bill.class,testData,this);
        System.out.println(billList.size());
        for(Bill billTemp:billList){
            System.out.println("对象List输出："+billTemp.getRemark());
        }
    }
}
