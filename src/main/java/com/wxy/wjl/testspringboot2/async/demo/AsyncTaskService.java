package com.wxy.wjl.testspringboot2.async.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


/**
 * 异步服务
 * 如果项目中未配置自定义线程池，则会使用springboot的默认线程池SimpleAsyncTaskExecutor，有OOM的风险
 * 建议使用自定义线程池
 */
@Service
public class AsyncTaskService {

    //使用自定义线程池
    @Async("asyncServiceExecutor")
    public Future<String> doTask1(String num) throws InterruptedException{
        System.out.println("开始任务 num ="+num);
        Thread.sleep(10000);
        return new AsyncResult<>(num);
    }

}
