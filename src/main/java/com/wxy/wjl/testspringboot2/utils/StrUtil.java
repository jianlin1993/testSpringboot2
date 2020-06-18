package com.wxy.wjl.testspringboot2.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtil {

    public static String getJrnNo(){
        return DateFormatUtils.format(new Date(),"yyyyMMdd")+String.valueOf((int)(Math.random()*9+1)*10000000);
    }

    public static String getExpTime(String curTime,String exp) throws Exception{
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate=simpleDateFormat.parse(curTime);
        Date expTime=new Date(curDate.getTime()+Long.parseLong(exp)*1000);
        return simpleDateFormat.format(expTime);
    }

    public static void main(String[] args) throws Exception{
        System.out.println(StrUtil.getExpTime("20200617102113","70"));
    }

}
