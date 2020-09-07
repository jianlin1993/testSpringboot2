package com.wxy.wjl.testspringboot2.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 本地文件复制   几种复制方式比较
 *
 * 直接缓冲区:不用写在应用程序的内存里面，直接写在物理地址上面
 */
public class NIO{

    public static  void main(String[] args) throws IOException {
        nioCopyTest2();
        nioCopyTest10();
    }

    /**通道之间的数据传输(直接缓冲区的模式)
     *
     */
    private static void   nioCopyTest3() throws IOException {
        long startTime = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("D:/1.avi"), StandardOpenOption.READ);

        FileChannel outChennel = FileChannel.open(Paths.get("D:/13.avi"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        outChennel.transferFrom(inChannel,0,inChannel.size());

        long end = System.currentTimeMillis();
        System.out.println("nio-transferFrom耗费时间:"+(end-startTime));
    }

    /**对比上面FileChannel不同的获取方式对效率的影响
     *
     */
    private static void   nioCopyTest6() throws IOException {
        long startTime = System.currentTimeMillis();

        FileChannel inChannel = new FileInputStream("D:/1.avi").getChannel();

        FileChannel outChennel = new FileOutputStream("D:/8.avi").getChannel();

        outChennel.transferFrom(inChannel,0,inChannel.size());

        long end = System.currentTimeMillis();
        System.out.println("nio-transferFrom2耗费时间:"+(end-startTime));
    }

    /**
     * 使用直接缓冲区完成文件的复制(内存映射文件)
     */
    private static void  nioCopyTest2() throws IOException {
        long startTime = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("D:/1.avi"), StandardOpenOption.READ);

        FileChannel outChennel = FileChannel.open(Paths.get("D:/12.avi"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        //内存映射文件(什么模式 从哪开始 到哪结束)
        MappedByteBuffer inMappeBuf = inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMappeBuf =  outChennel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());

        //直接都缓冲区进行数据的读写操作
        //byte[] dst = new byte[inMappeBuf.limit()];
        byte[] dst = new byte[1024];
        inMappeBuf.get(dst);
        outMappeBuf.put(dst);

        inChannel.close();
        outChennel.close();
        long end = System.currentTimeMillis();
        System.out.println("nio-MappedByteBuffer耗费时间:"+(end-startTime));
    }
    /**
     * 使用直接缓冲区完成文件的复制(内存映射文件)   分配缓冲区不同
     */
    private static void  nioCopyTest10() throws IOException {
        long startTime = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("D:/1.avi"), StandardOpenOption.READ);
        FileChannel outChennel = FileChannel.open(Paths.get("D:/6.avi"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        //内存映射文件(什么模式 从哪开始 到哪结束)
        MappedByteBuffer inMappeBuf = inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMappeBuf =  outChennel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());
        //直接都缓冲区进行数据的读写操作
        //byte[] dst = new byte[inMappeBuf.limit()];
        byte[] dst = new byte[2048];
        inMappeBuf.get(dst);
        outMappeBuf.put(dst);
        inChannel.close();
        outChennel.close();
        long end = System.currentTimeMillis();
        System.out.println("nio-MappedByteBuffer耗费时间:"+(end-startTime));
    }

    /**
     *  非直接缓冲区 文件的复制
     * @throws IOException
     */
    private static void nioCopyTest1()throws IOException {
        long startTime = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(new File("D:/1.avi"));
        FileOutputStream fileOutputStream = new FileOutputStream("D:/11.avi");

        //获取通道
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChanel = fileOutputStream.getChannel();

        //分配缓冲区的大小
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //将通道中的数据存入缓冲区
        while (inChannel.read(buf) != -1) {
            buf.flip();//切换读取数据的模式
            outChanel.write(buf);
            buf.clear();
        }
        outChanel.close();
        inChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("nio-ByteBuffer耗费时间:"+(end-startTime));
    }
    /**
     *  非直接缓冲区 文件的复制
     * @throws IOException
     */
    private static void nioCopyTest8()throws IOException {
        long startTime = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(new File("D:/1.avi"));
        FileOutputStream fileOutputStream = new FileOutputStream("D:/7.avi");
        //获取通道
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChanel = fileOutputStream.getChannel();
        //分配缓冲区的大小
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        //将通道中的数据存入缓冲区
        while (inChannel.read(buf) != -1) {
            buf.flip();//切换读取数据的模式
            outChanel.write(buf);
            buf.clear();
        }
        outChanel.close();
        inChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("nio-ByteBuffer耗费时间:"+(end-startTime));
    }

    /**
     *  传统IO
     * @throws IOException
     */
    private static void ioCopyTest4() throws IOException {
        long startTime = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(new File("D:/1.avi"));
        FileOutputStream fileOutputStream = new FileOutputStream("D:/10.avi");
        byte [] byte1=new byte[1024];
        int flag=-1;
        while((flag=fileInputStream.read(byte1)) != -1){
            fileOutputStream.write(byte1);
        }
        fileInputStream.close();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("io-FileInputStream耗费时间:"+(end-startTime));
    }

    /**
     *  传统IO 缓存包装类
     * @throws IOException
     */
    private static void ioCopyTest5() throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(new File("D:/1.avi")));
       BufferedOutputStream  fileOutputStream = new BufferedOutputStream(new FileOutputStream("D:/9.avi"));

        byte [] byte1=new byte[1024];
        int flag=-1;
        while((flag=fileInputStream.read(byte1)) != -1){
            fileOutputStream.write(byte1);
        }
        fileInputStream.close();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("io-BufferedInputStream耗费时间:"+(end-startTime));
    }
}

