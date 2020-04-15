package com.wxy.wjl.testspringboot2.Controller;


import com.wxy.wjl.testspringboot2.domain.Bill;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class helloController {

    @RequestMapping("/hello")
    private String index() {
        return "Hello World!";
    }

    /**
     * 测试堆中存放的是数据还是引用
     * @throws InterruptedException
     */
    @RequestMapping("/test")
    private void test() throws InterruptedException{
        int a=0;
        while(true){
            String s2=new String("sssssssssssssssssssss");
            Bill bill=new Bill();
            bill.setRemark("sssssssssssssssssssss");
            bill.setNo(1);
            Bill bill2=new Bill();
            bill2.setRemark(s2);
            bill2.setNo(2);
            Bill bill3=new Bill();
            bill3.setRemark("sssssssssssssssssssss");
            bill3.setNo(3);
            Bill bill4=new Bill();
            String s4=new String("sssssssssssssssssssss");
            bill4.setRemark(s4);
            bill4.setNo(4);
            Thread.sleep(2000);
            a++;
            if(a == 10){
                break;
            }
        }

    }

    @RequestMapping("/test2")
    private void test2() throws InterruptedException{
        int a=0;
        while(true){
            String s2=new String("sssssssssssssssssssss");
            Bill bill=new Bill();
            bill.setRemark("sssssssssssssssssssss");
            bill.setNo(1);
            Bill bill2=new Bill();
            bill2.setRemark(s2);
            bill2.setNo(2);
            Bill bill3=new Bill();
            bill3.setRemark("sssssssssssssssssssss");
            bill3.setNo(3);
            Bill bill4=new Bill();
            String s4=new String("sssssssssssssssssssss");
            bill4.setRemark(s4);
            bill4.setNo(4);
            Bill bill5=new Bill();
            String s5="sssssssssssssssssssss".intern();
            bill5.setRemark(s5);
            bill5.setNo(5);
            Thread.sleep(2000);
            a++;
            if(a == 10){
                break;
            }
        }

    }
}


