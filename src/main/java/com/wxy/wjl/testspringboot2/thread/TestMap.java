package com.wxy.wjl.testspringboot2.thread;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证hashmap与concurrentHashMap   put方法线程安全性
 */
public class TestMap {
   final static ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();;
    final static HashMap<Integer, Integer> hashMap = new HashMap<>();


    public static void main(String[] args) throws Exception{

        Thread thread1=new Thread(){
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    hashMap.put(i, i);
                    concurrentHashMap.put(i, i);
                }
            }
        };
        Thread thread2=new Thread(){
            public void run() {
                for (int i = 1000; i < 2000; i++) {
                    hashMap.put(i, i);
                    concurrentHashMap.put(i, i);
                }
            }
        };
        thread1.start();
        thread2.start();
        Thread.currentThread().sleep(2000);

        System.out.println(hashMap.size());
        System.out.println(concurrentHashMap.size());
    }
}