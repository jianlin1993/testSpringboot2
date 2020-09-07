package com.wxy.wjl.testspringboot2.io;

import com.wxy.wjl.testspringboot2.service.Son;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试IO流
 */
public class IO {

    /**
     * BufferedOutputStream
     *
     * @throws IOException
     */
    @Test
    public void testBufferedOutputStream() throws IOException {
        String fileName = "testBufferedOutputStream.txt";
        System.out.println("File.separator:" + File.separator);
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        try (BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(testFile))) {
            fo.write("测试缓存输出流".getBytes());
        }

    }

    /**
     * BufferedIntputStream
     *
     * @throws IOException
     */
    @Test
    public void testBufferedInputStream() throws IOException {
        String fileName = "testBufferedOutputStream.txt";
        System.out.println("File.separator:" + File.separator);
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        try (BufferedInputStream fo = new BufferedInputStream(new FileInputStream(testFile))) {
            byte[] b = new byte[2];  //此处有问题。读取文本文件时使用Reader
            int flag = -1;
            if ((flag = fo.read(b)) != -1) {
                System.out.println(new String(b));
            }
        }

    }

    /**
     * PrintStream
     *
     * @throws IOException
     */
    @Test
    public void testPrintStream() throws IOException {
        String fileName = "testPrintStream.txt";
        System.out.println("File.separator:" + File.separator);
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        try (PrintStream fo = new PrintStream(new FileOutputStream(testFile))) {
            fo.print("测试print用法");
        }

    }


    /**
     * 测试DataOutputStream
     *
     * @throws IOException
     */
    @Test
    public void testDataOutputStream() throws IOException {
        String fileName = "testDataOutputStream.txt";
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        String fileParentPath = testFile.getParent();//返回的是String类型
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        try (DataOutputStream fo = new DataOutputStream(new FileOutputStream(testFile))) {
            Son son = new Son();
            son.setAge(20);
            son.setGood(true);
            son.setFirstName("王");  //String不是java基本数据类型
            fo.writeInt(son.getAge());
            fo.writeBoolean(son.isGood());
            fo.writeChars(son.getFirstName());
        }

    }

    /**
     * 测试DataIutputStream-读取DataOutputStream写出的数据
     *
     * @throws IOException
     */
    @Test
    public void testDataIutputStream() throws IOException {
        String fileName = "testPrintStream.txt";
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        String fileParentPath = testFile.getParent();//返回的是String类型
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        try (DataInputStream fo = new DataInputStream(new FileInputStream(testFile))) {
            Son son = new Son();
            son.setAge(fo.readInt());  //读取一个int型的值
            son.setGood(fo.readBoolean());
            System.out.println(son.getAge() + " " + son.isGood());
        }

    }

    /**
     * NIOBuffer
     *
     * @throws IOException
     */
    @Test
    public void testNIOBuffer() throws IOException {
        String fileName = "testDataOutputStream.txt";
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        RandomAccessFile aFile = new RandomAccessFile(testFile, "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {
            buf.flip();  //make buffer ready for read
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // read 1 byte at a time
            }
            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();

    }

    /**
     * NIO  buffer中limit与position
     *
     * @throws IOException
     */
    @Test
    public void testNIOBuffer2() throws IOException {
        String fileName = "testPrintStream.txt";
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);

        FileInputStream file = new FileInputStream(testFile);
        FileChannel channel = file.getChannel();

        //初始化Buffer的长度为5,即使文件里面有多个元素,也只能读5个数据
        ByteBuffer byBuf = ByteBuffer.allocate(5);
        //buff的pos从0开始,limit是allocate初始化大小的(由于buf下表是从0开始的)5,channel的pos表示通道里面的文件读取数据的位置,也为0
        System.out.println("step1: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        //channel读取数据,即往Buffer里面写数据
        channel.read(byBuf);
        //在Buffer里面写完5个数据之后,buff的pos位置是5,byLimit的位置仍然是原来的位置5,由于读取文件读到了5个元素，所以chnanle的pos是5.
        System.out.println("step2: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        //将Buff的写状态转换为读状态,重置pos和limit,但是和channel没有关系。并没有重置channel
        byBuf.flip();
        //重置后,从0开始读取,最大可读的数量为5(里面只有5个元素),channel的pos不变
        System.out.println("step3: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        while (byBuf.remaining() > 0) {
            byte b = byBuf.get();
            System.out.println("Chanrset:" + (char) b);
        }
        //读取完之后Buf的pos及limit移动值
        System.out.println("step4: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        //再次重置,将Buf的读转换为往Buff里面写.
        byBuf.flip();
        //buff的pos被重置为0,buf的Limit被重置为上次最大可读取数据的位置,即step4的pos位置,由于和channel没有关系。所以channel的pos不变认为5
        System.out.println("step5: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        //在此读取数据,即往Buff里面写数据
        channel.read(byBuf);
        //由于channel的pos是5，里面有8个元素，上次读了5个剩余3个,所以在读完之后,pos的位置是3,，limit仍然保留上次的值5，channel的位置移动,移动到8
        System.out.println("step6: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        byBuf.flip();
        //由Buff的写数据转换为读数据,buf的pos被初始化为0,,limit被设置为最大可读取的位置,即step6的pos配置3.channel的pos是8
        System.out.println("step7: byBufPosition:" + byBuf.position() + ",byBufLimit:" + byBuf.limit() + ",channelPosition:" + channel.position());
        while (byBuf.remaining() > 0) {
            byte b = byBuf.get();
            System.out.println("Chanrset:" + (char) b);
        }

        //通过step4--step5和step6--step7可看出buf的limi是上一次pos的位置，不管是读还是写。即将要读或者写的最后一个元素的下一个元素的索引位置
        channel.close();


    }


    /**
     * testNIOFileChannel
     *
     * @throws IOException
     */

    @Test
    public void testNIOFileChannel() throws IOException {
        String fileName = "testNIOFileChannel.txt";
        File testFile = new File("D:" + File.separator + "IOTest" + File.separator + fileName);
        RandomAccessFile aFile = new RandomAccessFile(testFile, "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {
            buf.flip();  //make buffer ready for read
            while (buf.hasRemaining()) {
                //System.out.print((char) buf.get()); // read 1 byte at a time
                System.out.println((char)buf.get());
            }
            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();

    }



}



