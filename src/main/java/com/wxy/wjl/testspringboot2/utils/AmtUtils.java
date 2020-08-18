package com.wxy.wjl.testspringboot2.utils;

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

}
