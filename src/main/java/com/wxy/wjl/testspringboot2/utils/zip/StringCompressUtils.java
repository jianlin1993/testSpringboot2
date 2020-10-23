package com.wxy.wjl.testspringboot2.utils.zip;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * Java 字符串压缩工具
 * 
 * @author Logan
 * @version 1.0.0
 *
 */
public class StringCompressUtils {

    public static final String MR_PART="<mrgzip>";

    /**
     * 使用gzip进行压缩 此处不能把ByteArrayOutputStream也写到try里面
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     * @throws IOException 有异常时抛出，由调用者捕获处理
     */
    public static String gzip(String str) throws IOException {
        if (str == null || str.isEmpty()) {
            return str;
        }
        ByteArrayOutputStream out=null;
        GZIPOutputStream gzip=null;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
        }finally{
            gzip.close();
            out.close();
        }
        byte [] result =  out.toByteArray();
        return Base64.encodeBase64String(result);
    }


    /**
     * 使用gzip进行解压缩
     *
     * @param compressedStr 压缩字符串
     * @return 解压字符串
     * @throws IOException 有异常时抛出，由调用者捕获处理
     */
    public static String gunzip(String compressedStr) throws IOException {
        if (compressedStr == null || compressedStr.isEmpty()) {
            return compressedStr;
        }
        byte[] compressed = Base64.decodeBase64(compressedStr);
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ByteArrayInputStream in = new ByteArrayInputStream(compressed);
                GZIPInputStream ginzip = new GZIPInputStream(in);
        ) {
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toString();
        }
    }


    /**
     * 此方法与上面gzip方法是一样的  因为ByteArrayOutputStream不需要关闭
     * @param str
     * @return
     * @throws IOException
     */
    public static String gzip2(String str) throws IOException {
        if (str == null || str.isEmpty()) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (
                GZIPOutputStream gzip = new GZIPOutputStream(out);
        ) {
            gzip.write(str.getBytes());
        }
        return Base64.encodeBase64String(out.toByteArray());
    }


    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     * @throws IOException 有异常时抛出，由调用者捕获处理
     */
    public static String zip(String str) throws IOException {
        if (null == str || str.isEmpty()) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (
                ZipOutputStream zout = new ZipOutputStream(out);
        ) {
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
        }
        return Base64.encodeBase64String(out.toByteArray());
    }

    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     * @throws IOException 有异常时抛出，由调用者捕获处理
     */
    public static final String unzip(String compressedStr) throws IOException {
        if (null == compressedStr || compressedStr.isEmpty()) {
            return compressedStr;
        }

        byte[] compressed = Base64.decodeBase64(compressedStr);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(compressed);

        try (
                ZipInputStream zin = new ZipInputStream(in);
        ) {
            zin.getNextEntry();
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = zin.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
        return out.toString();
    }

    public static void main(String[] args) throws Exception{
  //      String afterCom="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";
        String s= " <mrgzip>aa<mrgzip> <mrgzip>bb";
        String afterStr="H4sIAAAAAAAAAFOwyS1Kr8ossEtMhLEUYIykJADdvrvuHgAAAA==";
        System.out.println("字符串长度："+s.length());
        System.out.println("压缩后：："+" 长度 ="+gzip(s).length() + " 报文："+gzip(s));
        System.out.println("解压后："+" 长度 ="+gunzip(afterStr).length());
        String [] after=gunzip(afterStr).split("<mrgzip>");
        for(int i=0;i<after.length;i++){
            System.out.println("分割后："+after[i]);
        }
//        System.out.println("解压前："+" 长度 ="+afterCom.length());
//        System.out.println("解压后："+" 长度 ="+gunzip(afterCom).length());
//        System.out.println("解压后："+" 报文 ="+gunzip(afterCom));
    }
}