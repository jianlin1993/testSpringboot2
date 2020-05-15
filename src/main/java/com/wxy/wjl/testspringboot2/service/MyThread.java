package com.wxy.wjl.testspringboot2.service;

public class MyThread implements Runnable{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
    public static void main(String[] args) {
        //首先需要使用MyThread实例来创建一个Thread实例
        MyThread myThread=new MyThread();
        Thread thread=new Thread(myThread);
        thread.start();
    }
}
