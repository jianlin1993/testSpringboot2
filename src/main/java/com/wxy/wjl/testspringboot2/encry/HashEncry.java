package com.wxy.wjl.testspringboot2.encry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Hash散列
 * MD5 SHA1 SHA256 Hmac
 */
public class HashEncry {

    //String转byte数组  编码
    public static final String CHARSET = "UTF-8";


    //Hmac加密模式
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * MD5 算法
     * 无论是多长的输入，MD5 都会输出长度为 128bits 的一个串 (通常用 16 进制 表示为 32 个字符)。
     *
     * @param content
     * @return
     */
    public static String computeMD5(String content) {
        if (null == content) {
            return null;
        }
        try {
            MessageDigest sha1 = MessageDigest.getInstance("MD5");
            return byte2Hex(sha1.digest(content.getBytes(CHARSET)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA1 算法
     * SHA1 会产生一个 160 位的 消息摘要
     *
     * @param content
     * @return
     */
    public static String computeSHA1(String content) {
        if (null == content) {
            return null;
        }
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            return byte2Hex(sha1.digest(content.getBytes(CHARSET)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA256 算法
     * 对于任意长度的消息，SHA256都会产生一个256bit长的哈希值，称作消息摘要。
     * 这个摘要相当于是个长度为32个字节的数组，通常用一个长度为64的十六进制字符串来表示
     *
     * @param content
     * @return
     */
    public static String computeSHA256(String content) {
        if (null == content) {
            return null;
        }
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-256");
            return byte2Hex(sha1.digest(content.getBytes(CHARSET)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * HMAC加密
     * Hmac输出的消息摘要长度  取决于用的模式（例：HmacMD5输出与MD5相同128bit）
     *
     *
     * @param content 需要加密的字符串
     * @param key 密钥
     *  @return 字符串
     *
     *  密钥key可能是base64 编码之后的String  可能需要base64解码
     *
     *
     */
    public static String computeHMAC(String content, String key) {
        if (null == content) {
            return null;
        }
        SecretKey secretKey;
        try {
            secretKey = new SecretKeySpec(key.getBytes(CHARSET), HMAC_MD5);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return byte2Hex(mac.doFinal(content.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * base64编码
     * Base64是一种用64个字符来表示任意二进制数据的方法。
     * 按照一定规则分组，之后通过查编码表进行转换
     * @param key
     * @return
     */
    public static String base64Encry(String key){
        if(null == key){
            return null;
        }
        String decodedKey=null;
        try{
            decodedKey= new String(Base64.encodeBase64(key.getBytes(CHARSET)));
            return decodedKey;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * base64解码
     *
     */
    public static String base64Decry(String key){
        if(null == key){
            return null;
        }
        String decodedKey=null;
        try{
            decodedKey=new String(Base64.decodeBase64(key.getBytes(CHARSET)));
            return decodedKey;
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }




        /**
         * 二进制字节数组转16进制字符串
         * @param bytes
         * @return
         */
        public static String byte2Hex ( byte[] bytes){
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            for (int i = 0; i < bytes.length; i++) {
                //  &表示按位与,只有两个位同时为1,才能得到1, 0x代表16进制数,
                //  0xff表示的数二进制1111 1111 占一个字节.和其进行&操作的数,最低8位,不会发生变化.
                temp = Integer.toHexString(bytes[i] & 0xFF);
                if (temp.length() == 1) {
                    //1得到一位的进行补0操作
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }


        public static void main (String[]args){
            String msg = "测试一下各个hash算法";
            System.out.println("md5---" + computeMD5(msg));
            System.out.println("sha1---" + computeSHA1(msg));
            System.out.println("sha256---" + computeSHA256(msg));
            System.out.println("Hmac---" + computeHMAC(msg,"123"));

            System.out.println("对123进行base64编码:"+base64Encry("123"));
            System.out.println("对123进行base64编码后再解码:"+base64Decry(base64Encry("123")));
            System.out.println("对123进行base64编码后再解码 再用Hmac加密:"+computeHMAC(msg,base64Decry(base64Encry("123"))));
        }


        /**
         * MD5
         * 自己算的16进制
         * @param s
         * @return
         */
        public final static String MD5 (String s){
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                byte[] btInput = s.getBytes();
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }
