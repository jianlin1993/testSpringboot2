package com.wxy.wjl.testspringboot2.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;


@Slf4j
public class MDCUtils {

    /** 日志号key */
    private static final String TRACE_ID = "TRACE_ID";

    private MDCUtils() {

    }

    /**
     * 设置traceId
     */
    public static void setTraceId() {
        MDC.put(TRACE_ID, randomUUID());
    }

    /**
     * 获取 traceId
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 清除traceId
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID);
    }

    /**
     * 生成随机32位的UUID  保证世界上所有机器生成的uuid唯一
     * @return UUID
     */
    private static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
