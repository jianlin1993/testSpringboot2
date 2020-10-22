package com.wxy.wjl.testspringboot2.utils.zip;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
    public static final int BUFFER = 1024;
    public static final String EXT = ".gz";


    /**
     * 压缩 字符串 to 字符串
     * @param str
     * @return
     * @throws Exception
     */
    public static String compressToString(String str) throws Exception{
        return  Base64.encodeBase64String(compress(str.getBytes()));
    }

    /**
     * 解压 字符串 to 字符串
     * @param str
     * @return
     * @throws Exception
     */
    public static String decompressToString(String str) throws Exception{
        return  Base64.encodeBase64String(decompress(str.getBytes()));
    }



    public static byte[] compress(byte[] var0) throws Exception {
        ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        compress(var1, var2);
        byte[] var3 = var2.toByteArray();
        var2.flush();
        var2.close();
        var1.close();
        return var3;
    }

    public static void compress(File var0) throws Exception {
        compress(var0, true);
    }

    public static void compress(File var0, boolean var1) throws Exception {
        FileInputStream var2 = new FileInputStream(var0);
        FileOutputStream var3 = new FileOutputStream(var0.getPath() + ".gz");
        compress(var2, var3);
        var2.close();
        var3.flush();
        var3.close();
        if (var1) {
            var0.delete();
        }

    }

    public static void compress(InputStream var0, OutputStream var1) throws Exception {
        GZIPOutputStream var2 = new GZIPOutputStream(var1);
        byte[] var4 = new byte[1024];

        int var3;
        while((var3 = var0.read(var4, 0, 1024)) != -1) {
            var2.write(var4, 0, var3);
        }

        var2.finish();
        var2.flush();
        var2.close();
    }

    public static void compress(String var0) throws Exception {
        compress(var0, true);
    }

    public static void compress(String var0, boolean var1) throws Exception {
        File var2 = new File(var0);
        compress(var2, var1);
    }

    public static byte[] decompress(byte[] var0) throws Exception {
        ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        decompress(var1, var2);
        var0 = var2.toByteArray();
        var2.flush();
        var2.close();
        var1.close();
        return var0;
    }

    public static void decompress(File var0) throws Exception {
        decompress(var0, true);
    }

    public static void decompress(File var0, boolean var1) throws Exception {
        FileInputStream var2 = new FileInputStream(var0);
        FileOutputStream var3 = new FileOutputStream(var0.getPath().replace(".gz", ""));
        decompress(var2, var3);
        var2.close();
        var3.flush();
        var3.close();
        if (var1) {
            var0.delete();
        }

    }

    public static void decompress(InputStream var0, OutputStream var1) throws Exception {
        GZIPInputStream var2 = new GZIPInputStream(var0);
        byte[] var4 = new byte[1024];

        int var3;
        while((var3 = var2.read(var4, 0, 1024)) != -1) {
            var1.write(var4, 0, var3);
        }

        var2.close();
    }

    public static void decompress(String var0) throws Exception {
        decompress(var0, true);
    }

    public static void decompress(String var0, boolean var1) throws Exception {
        File var2 = new File(var0);
        decompress(var2, var1);
    }


    public static void main(String[] args) throws Exception{
        String s= " <mrgzip>aa<mrgzip> <mrgzip>bb";
        String afterStr="H4sIAAAAAAAAAFOwyS1Kr8ossEtMhLEUYIykJADdvrvuHgAAAA==";
        System.out.println("字符串长度："+s.length());
        System.out.println("压缩后：："+" 长度 ="+compressToString(s).length() + " 报文："+compressToString(s));
        System.out.println("解压后："+" 长度 ="+decompressToString(afterStr).length());
        String [] after=compressToString(afterStr).split("<mrgzip>");
        for(int i=0;i<after.length;i++){
            System.out.println("分割后："+after[i]);
        }
    }
}
