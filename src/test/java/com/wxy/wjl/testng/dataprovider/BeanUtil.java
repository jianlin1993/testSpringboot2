package com.wxy.wjl.testng.dataprovider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanUtil {
    private static ApplicationContext context;

    public BeanUtil(){};

    private static void defaultInitialize(){
        if(context == null){
            //initialize("src/test/resources/config/*.xml");
            initialize("classpath*:config/*.xml");
        }
    }

    private static void initialize(String config){
        context=new ClassPathXmlApplicationContext(config);
    }

    public static <T> T getBean(String beanId){
        defaultInitialize();
        return (T)context.getBean(beanId);
    }

    public static <T> T getBean(String beanId,String config){
        initialize(config);
        return (T)context.getBean(beanId);
    }

}
