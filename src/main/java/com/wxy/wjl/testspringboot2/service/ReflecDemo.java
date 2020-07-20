package com.wxy.wjl.testspringboot2.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射测试
 */
public class ReflecDemo {

    public static void main (String[] args) throws Exception{
        //获取Son类的Class对象
        Class clazz=Class.forName("com.wxy.wjl.testspringboot2.domain.Student");
        //获取 Person 类的所有方法信息
/*        Method[] method=clazz.getDeclaredMethods();
        for(Method m:method){
            System.out.println(m.toString());
        }*/
        //获取 Person 类的所有成员属性信息
        Field[] field=clazz.getDeclaredFields();
        for(Field f:field){
            System.out.println(f.getType() + " 是否是java本身类型"+isJavaClass(f.getType())
                    + "类加载器="+f.getType().getClassLoader() + " field.getGenericType().getTypeName():"+f.getGenericType().getTypeName()
            + "  getGenericType :"+f.getGenericType());

            if(f.getName().equals("test2")){
                System.out.println("------------------"+List.class.equals(f.getType()));
            }
        }
/*        //获取 Person 类的所有构造方法信息
        Constructor[] constructor=clazz.getDeclaredConstructors();
        for(Constructor c:constructor){
            System.out.println(c.toString());
        }*/



    }

    public static boolean isJavaClass(Class clazz){
        return clazz != null &&  clazz.getClassLoader() == null;
    }



}
