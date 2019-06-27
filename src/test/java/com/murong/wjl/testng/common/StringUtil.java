package com.murong.wjl.testng.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class StringUtil {

    public static String getJrnNo(){
        return DateFormatUtils.format(new Date(),"yyyyMMdd")+String.valueOf((int)(Math.random()*9+1)*10000000);
    }

    public static String getDateString(){
        return DateFormatUtils.format(new Date(),"yyyyMMdd");
    }
}
