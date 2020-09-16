package com.wxy.wjl.testspringboot2.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


/**
 * 异步服务
 */
@Service
public class AsyncTaskService {

    @Async
    public Future<String> doTask1(String num) throws InterruptedException{
        System.out.println("开始任务 num ="+num);
        Thread.sleep(10000);
        return new AsyncResult<>(num);
    }

}
