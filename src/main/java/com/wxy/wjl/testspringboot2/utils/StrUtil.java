package com.wxy.wjl.testspringboot2.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class StrUtil {

    private static AtomicLong seqNo = new AtomicLong();

    /**
     * 获取唯一流水号
     * @return
     */
    public static String getUniqueId()
    {
        synchronized (seqNo) {
            long id = seqNo.incrementAndGet();
            if (id > 999999L) {
                seqNo.set(0L);
                id = 0L;
            }
            return getCurrDateTime() + StringUtils.leftPad(String.valueOf(id), 6, '0');
        }
    }
    public static String getCurrDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }


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
        //System.out.println(StrUtil.getExpTime("20200617102113","70"));
        System.out.println("  DELETE FROM ".indexOf("FROM"));
    }

}
