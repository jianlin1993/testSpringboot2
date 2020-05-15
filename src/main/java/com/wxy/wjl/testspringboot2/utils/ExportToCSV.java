package com.wxy.wjl.testspringboot2.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * io和Nio生成文件
 */
public class ExportToCSV {

    public static boolean exportToCsvIO(String filePath, List<String> dataList){
        boolean isSucess=false;
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(filePath);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }


    public static boolean exportToCsvNIO(String filePath, List<String> dataList) throws Exception{
        boolean isSucess=false;
        FileOutputStream out=null;
        FileChannel fileChannel=null;
        try {
            out = new FileOutputStream(filePath);
            fileChannel=out.getChannel();
            ByteBuffer byteBuffer=ByteBuffer.allocate(2);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    byteBuffer.put(data.getBytes());
                }
            }
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                fileChannel.write(byteBuffer);
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            fileChannel.close();
            out.close();

        }
        return isSucess;
    }


    public static void main(String[] args) throws Exception{
        List<String> list=new ArrayList<>();
        list.add("壹");
        list.add("二");
        exportToCsvNIO("D:/IOTest/ExportToCSVNIO.csv",list);
        exportToCsvIO("D:/IOTest/ExportToCSVIO.csv",list);
    }

}
