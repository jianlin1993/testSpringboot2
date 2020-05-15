package com.wxy.wjl.testspringboot2.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证hashmap与concurrentHashMap   put方法线程安全性
 */
public class TestHashMap {
    public static final int N = 5;

    public static void main(String[] args) throws Exception{
        final HashMap<String, Integer> mapTest = new HashMap<String, Integer>();
        final ConcurrentHashMap<String, Integer> mapTest2 = new ConcurrentHashMap<String, Integer>();
        final List<Thread> threadList = new ArrayList<Thread>(N);
        for(int i=0; i<N; i++){
            threadList.add(new Thread(()->{
                for(int j=0; j<10000; j++){
                    mapTest.put(Thread.currentThread().getId()+"key"+j, j);
                    mapTest2.put(Thread.currentThread().getId()+"key"+j, j);
                }
            }));
        }
        for(Thread thread : threadList){
            thread.start();
        }
        for(Thread thread : threadList){
            thread.join();
        }
        System.out.println(mapTest.size() + "  "+mapTest2.size());
    }
}

