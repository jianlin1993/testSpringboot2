package com.wxy.wjl.testspringboot2.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射测试
 */
public class ReflecDemo {

    public static void main (String[] args) throws Exception{
        //获取Son类的Class对象
        Class clazz=Class.forName("com.wxy.wjl.testspringboot2.domain.Student");
        //获取 Person 类的所有方法信息
        Method[] method=clazz.getDeclaredMethods();
        for(Method m:method){
            System.out.println(m.toString());
        }
        //获取 Person 类的所有成员属性信息
        Field[] field=clazz.getDeclaredFields();
        for(Field f:field){
            System.out.println(f.toString());
        }
        //获取 Person 类的所有构造方法信息
        Constructor[] constructor=clazz.getDeclaredConstructors();
        for(Constructor c:constructor){
            System.out.println(c.toString());
        }



    }





}
