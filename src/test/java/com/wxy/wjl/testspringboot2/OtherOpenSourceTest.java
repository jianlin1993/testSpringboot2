package com.wxy.wjl.testspringboot2;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

/**
 * 其他开源工具的测试类
 */
public class OtherOpenSourceTest {

    /**
     * AviatorEvaluator表达式求值
     */
    @Test
    public void testAviatorEvaluator(){
        System.out.println(AviatorEvaluator.execute("2 == 1+1"));
    }

}
