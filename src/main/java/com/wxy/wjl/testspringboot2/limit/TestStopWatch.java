package com.wxy.wjl.testspringboot2.limit;


import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * StopWatch 实现计时
 */
public class TestStopWatch {
    public static void main(String[] args) throws Exception{
        StopWatch stopWatch=StopWatch.createStarted();
        Thread.sleep(10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.SECONDS));
    }

    @Test
    public void testStopWatch() throws Exception{
        StopWatch stopWatch=StopWatch.createStarted();
        Thread.sleep(10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.SECONDS));
    }


    @Test
    public void testSimple() throws Exception{
        StopWatch stopWatch=null;
        RateLimiter rateLimiter = RateLimiter.create(5.0D);
        for (int i = 0; i < 10; i++) {
            if(i == 0){
                stopWatch=StopWatch.createStarted();
            }
            if (rateLimiter.tryAcquire()) {
                System.out.println(LocalTime.now() + " 通过");
            } else {
                System.out.println(LocalTime.now() + " 被限流");
            }
            if(i == 9){
                stopWatch.stop();
                System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
            }
        }
    }

}
