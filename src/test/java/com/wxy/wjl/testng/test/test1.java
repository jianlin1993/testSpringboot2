package com.wxy.wjl.testng.test;

import com.wxy.wjl.testng.TestNGBaseTest;
import com.wxy.wjl.testng.TestNGListener;
import com.wxy.wjl.testng.TestNGRetryListener;
import com.wxy.wjl.testng.dataprovider.BeanUtil;
import com.wxy.wjl.testng.dbUtils.DbUtilsHelp;
import com.wxy.wjl.testspringboot2.Controller.billController;
import com.wxy.wjl.testspringboot2.domain.Bill;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Listeners({TestNGListener.class})
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

    //db测试
    @Test
    public void testDBSelectCol(){
        Object object=dbUtilsHelp.selectCol("select * from usr;","USR_NM");
        System.out.println(object);
    }

    //db测试
    @Test
    public void testDBSelectOne(){
        Object object=dbUtilsHelp.selectOne("select * from usr where USR_NM = '王建林';");
        System.out.println(object);
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
        String num=testData.get("TEST_CASE_ID")+"    ";
        System.out.println(num);
        billControllerBean.sout();
        //构建对象
//        Bill bill=buildJavaBeanModel("Bill",Bill.class,testData,this);
        //System.out.println("单对象输出："+bill.getRemark());

        //构建对象list
        List<Bill> billList=buildJavaBeanListModel("BillList",Bill.class,testData,this);
        System.out.println(billList.size());
        for(Bill billTemp:billList){
            System.out.println("对象List输出："+billTemp.getRemark());
        }
            //Assert.assertEquals(1,0);

    }
}
