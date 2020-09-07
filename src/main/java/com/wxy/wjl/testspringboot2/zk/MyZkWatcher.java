package com.wxy.wjl.testspringboot2.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class MyZkWatcher implements Watcher {

    //异步锁
    private CountDownLatch cdl;

    //标记
    private String mark;

    public MyZkWatcher(CountDownLatch cdl,String mark) {
        this.cdl = cdl;
        this.mark = mark;
    }

    //监听事件处理方法
    public void process(WatchedEvent event) {
        log.info(mark+" watcher监听事件：{}",event);
        cdl.countDown();
    }
}
