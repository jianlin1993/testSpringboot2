package com.wxy.wjl.testspringboot2.utils.sftp;

import com.wxy.wjl.testspringboot2.thread.Deadlock;

public class ThreadSftpTest {

    public static void main(String[] args) {
        ThreadSftpTest.Thread1 thread1 = new ThreadSftpTest.Thread1();
        Thread thread = new Thread(thread1);
        thread.start();
        ThreadSftpTest.Thread2 thread2 = new ThreadSftpTest.Thread2();
        Thread threadX = new Thread(thread2);
        threadX.start();
    }


    static class Thread1 implements Runnable {
        @Override
        public void run() {
            try{
                MrFileUtilsSftpExt.upload("D:/home/filedir","dfs/testupload3/","test.txt");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }}}
    static class Thread2 implements Runnable {
        @Override
        public void run() {
            try{
                MrFileUtilsSftpExt.upload("D:/home/filedir","dfs/testupload3/","test.txt");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }}}

}
