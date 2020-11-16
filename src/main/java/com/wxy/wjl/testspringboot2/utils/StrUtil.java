package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.fastjson.JSON;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.wxy.wjl.testspringboot2.cache.CustomizeExpression;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class StrUtil {

    /**
     * Mybatis-plus的主键生成规则-UUID（其他的主键规则不靠谱）
     * @return
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return (new UUID(random.nextLong(), random.nextLong())).toString().replace("-", "");
    }

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
        Map<String,Object> map=new HashMap<>();
        map.put("a","1");
        System.out.println(execute("mr.strEq(a,'1')",map));
        //更为复杂的对象可用如下形式
        String string = "{\"he\":\"1\", \"person\": {\"age\": 1.1, \"sex\":\"afa\"}}";
        System.out.println(execute("mr.strEq(person.sex, 'afa')", JSON.parseObject(string)));

//        System.out.println(execute("mr.strEq(a,'1')",map));
//        System.out.println(execute("mr.numEq(a,1)",map));
    }

    /**
     * 执行表达式
     * @param expr   待执行的表达式
     * @param params 表达式执行参数
     * @return 表达式执行结果
     */
    public static Object execute(String expr, Map<String, Object> params) {
        try {
            AviatorEvaluator.addStaticFunctions("mr", CustomizeExpression.class);
            Expression expression = AviatorEvaluator.compile(expr);
            //Expression expression = optional.orElse(null);
            if (expression == null) {
                return null;
            }
            return expression.execute(params);
        } catch (Exception e) {
            return null;
        }
    }


}
