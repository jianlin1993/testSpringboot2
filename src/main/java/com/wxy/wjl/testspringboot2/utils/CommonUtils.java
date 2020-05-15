package com.wxy.wjl.testspringboot2.utils;



import com.wxy.wjl.testspringboot2.enums.BusCdEnum;
import com.wxy.wjl.testspringboot2.enums.BusMngCdEnum;

import java.util.List;

public class CommonUtils {

    /**
     * 根据BusMngCd转换BusCd
     *
     * @return 拼接字符串
     */
    public static String busMngCdConvert(String BusMngCd) {

        List<BusCdEnum> list=BusCdEnum.getByBusMngCd(BusMngCdEnum.getByValue(BusMngCd));
        if(list == null || list.isEmpty()){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer("(");
        for(int i=0;i<list.size();i++){
            if(i != list.size()-1){
                stringBuffer.append("'"+list.get(i).getValue()+"',");
            }else{
                stringBuffer.append("'"+list.get(i).getValue()+"')");
            }
        }
        return stringBuffer.toString();


    }
}
