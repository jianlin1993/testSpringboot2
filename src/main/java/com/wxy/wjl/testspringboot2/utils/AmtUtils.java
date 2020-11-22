package com.wxy.wjl.testspringboot2.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class AmtUtils {

    // 保留两位小数，每三位用逗号分隔
    private static final String defaultFormat="#,##0.00";

    public static BigDecimal amtFormat(BigDecimal originalAmt){
        return new BigDecimal(new DecimalFormat(defaultFormat).format(originalAmt));
    }

    public static BigDecimal amtFormat(BigDecimal originalAmt,String formatter){
        try{
            return new BigDecimal(new DecimalFormat(formatter).format(originalAmt));
        }catch (Exception e){
            throw e;
        }
    }

    public static String yuan2fen(String yuanAmt) {
        if(StringUtils.isBlank(yuanAmt)){
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#");
        BigDecimal yuan=new BigDecimal(yuanAmt);
        BigDecimal fen=yuan.multiply(new BigDecimal("100"));
        return df.format(fen);
    }

    public static void main(String[] args) {
        System.out.println(yuan2fen(""));
    }

}
