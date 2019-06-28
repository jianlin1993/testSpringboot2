package com.wxy.wjl.testspringboot2.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class StrUtil {

    public static String getJrnNo(){
        return DateFormatUtils.format(new Date(),"yyyyMMdd")+String.valueOf((int)(Math.random()*9+1)*10000000);
    }

}
