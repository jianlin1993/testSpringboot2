package com.wxy.wjl.testspringboot2.service;

import java.lang.reflect.Field;

public class IdInfoUtil {
    public static void getIdInof(Class<?> clazz) {
        String idInfo = "身份信息：";
        Field[] fields = clazz.getDeclaredFields();//通过反射获取处理注解
        for (Field field : fields) {
            if (field.isAnnotationPresent(IdInformation.class)) {
                IdInformation idInformation = (IdInformation) field.getAnnotation(IdInformation.class);
                //注解信息的处理地方
                idInfo = " 身份证：" + idInformation.id() + " 名称："
                        + idInformation.name() + " 体重：" + idInformation.bodyWeight();
                System.out.println(idInfo);
            }
        }
    }
    public static void main(String[] args) {
        IdInfoUtil.getIdInof(Person.class);
    }
}
