package com.wxy.wjl.testspringboot2.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义表达式
 * @author chenjunyi
 * Created by chenjunyi on 2020/1/9
 */
@Slf4j
public class CustomizeExpression {

    private CustomizeExpression() {

    }

    /**
     * 字符串相等比较
     * @param string1 比较字符串1
     * @param string2 比较字符串2
     * @return true-相等；false-不等
     */
    public static boolean strEq(String string1, String string2) {
        return StringUtils.equals(string1, string2);
    }

    /**
     * 字符串不相等比较
     * @param string1 比较字符串1
     * @param string2 比较字符串2
     * @return true-不等；false-相等
     */
    public static boolean strNe(String string1, String string2) {
        return !strEq(string1, string2);
    }

    /**
     * 数字小于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-小于；false-不小于
     */
    public static boolean numLt(double number1, double number2) {
        return number1 < number2;
    }

    /**
     * 数字小于等于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-小于等于；false-大于
     */
    public static boolean numLe(double number1, double number2) {
        return number1 <= number2;
    }

    /**
     * 数字大于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-大于；false-不大于
     */
    public static boolean numGt(double number1, double number2) {
        return number1 > number2;
    }

    /**
     * 数字大于等于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-大于等于；false-小于
     */
    public static boolean numGe(double number1, double number2) {
        return number1 >= number2;
    }

    /**
     * 数字等于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-等于；false-不等于
     */
    public static boolean numEq(double number1, double number2) {
        return Double.doubleToLongBits(number1) == Double.doubleToLongBits(number2);
    }

    /**
     * 数字不等于比较
     * @param number1 比较数字1
     * @param number2 比较数字2
     * @return true-不等于；false-等于
     */
    public static boolean numNe(double number1, double number2) {
        return !numEq(number1, number2);
    }

}
