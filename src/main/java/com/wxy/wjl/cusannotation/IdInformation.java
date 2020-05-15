package com.wxy.wjl.cusannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdInformation {
    int id() default -1; //身份证
    String name(); //名字
    int bodyWeight(); //体重
}
