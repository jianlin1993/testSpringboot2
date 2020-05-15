package com.wxy.wjl.testspringboot2.thread;

public class Deadlock {
    static String s1 = "a";
    static String s2 = "b";

    public static void main(String[] args) {
        Deadlock.Thread1 thread1 = new Deadlock.Thread1();
        Thread thread = new Thread(thread1);
        thread.start();
        Deadlock.Thread2 thread2 = new Deadlock.Thread2();
        Thread threadX = new Thread(thread2);
        threadX.start();
    }
    static class Thread1 implements Runnable {
        @Override
        public void run() {
            try { while (true) {
                synchronized (s1) {
                    Thread.sleep(3000);
                    System.out.println("Thread1持有s1锁");
                    synchronized (s2) {
                        System.out.println("Thread1持有s2锁");
                    } } }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } }}
    static class Thread2 implements Runnable {
        @Override
        public void run() {
            try { while (true) {
                synchronized (s2) {
                    Thread.sleep(3000);
                    System.out.println("Thread2持有s2锁");
                    synchronized (s1) {
                        System.out.println("Thread2持有s1锁");
                    } } }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } }}
}
