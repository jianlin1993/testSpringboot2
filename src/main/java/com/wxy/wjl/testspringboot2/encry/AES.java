package com.wxy.wjl.testspringboot2.encry;



import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;

/**
 * 高级加密算法
 * 对称加密
 * java默认AES： ECB / PKCS5Padding
 */
public class AES {

    // jdk8之后首选自带的base64编码
   static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();

    // 密文编码方式
    private static final String BASE64_MODE = "base64";
    private static final String HEX_MODE = "hex";



    //String转byte数组  编码
    private static final String CHARSET = "UTF-8";

    // 默认密钥 32bytes 256bits
    private static final String defaultKey = "c0e9fcff59ecc3b8b92939a1a2724a44";

    //偏移量字符串必须是16字节 当模式是CBC的时候必须设置偏移量
    private static final String iv = "1111111111111111";
    private static final String Algorithm = "AES";
    //算法/模式/补码方式
    private static final String AlgorithmProvider = "AES/CBC/PKCS5Padding";
    /**
     *
     *  生成key，作为加密和解密密钥且只有密钥相同解密加密才会成功
     *
     * @return
     */
    public static Key createKey(String key) {

        try {
            // 生成key
            KeyGenerator keyGenerator;
            //构造密钥生成器，指定为AES算法,不区分大小写
            keyGenerator = KeyGenerator.getInstance("AES");
            //生成一个128位的随机源,根据传入的字节数组  此为AES128加密，如果生成256位，则安全性更高
            keyGenerator.init(256,new SecureRandom(key.getBytes(CHARSET)));
            //产生原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得原始对称密钥的字节数组
            byte[] keyBytes = secretKey.getEncoded();
            // key转换,根据字节数组生成AES密钥
            return new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**加密
     * @param context 需要加密的明文
     * @param key 加密用密钥
     * @return
     */
    public static byte[] jdkAES(String context, Key key) {
        if (null == context) {
            return null;
        }
        try {
            //加密模式   ECB   填充方式  PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(cipher.ENCRYPT_MODE, key);
            //将加密并编码后的内容解码成字节数组
            byte[] result = cipher.doFinal(context.getBytes());

            //System.out.println("jdk aes:" + Base64.encode(result));

            return result;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 解密
     * @param result 加密后的密文byte数组
     * @param key 解密用密钥
     */
    public static byte[] decrypt(byte[] result, Key key) {

        Cipher cipher;
        try {

            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            // 当模式是CBC的时候必须设置偏移量 偏移量字符串必须是16位
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
        //System.out.println("jdk aes desrypt:" + new String(result));
    }

    /**---------------------------------------------CBC模式-----------------------------------------------*/

    /**
     * 获取偏移量  CBC模式使用,偏移量长度必须是16字节
     * @return
     * @throws UnsupportedEncodingException
     */
    public static IvParameterSpec getIv() throws UnsupportedEncodingException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        return ivParameterSpec;
    }

    /**
     * 获取默认key
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getDefaultKey() throws UnsupportedEncodingException{
        return defaultKey.getBytes("UTF-8");
    }


    /**
     * CBC模式加密  此处是返回字节数组
     * @param src
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encryptCBC(String src, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
        return cipherBytes;
    }

    /**
     * CBC解密
     * @param src  密文
     * @param key   密钥
     * @param mode  密文编码模式  base64/hex
     * @return
     * @throws Exception
     */
    public static byte[] decryptCBC(String src, byte[] key ,String mode) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);

        IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] hexBytes = null;
        if(StringUtils.equals(mode,BASE64_MODE)){
            hexBytes = decoder.decode(src);
        }else if(StringUtils.equals(mode,HEX_MODE)){
            hexBytes = parseHexStr2Byte(src);
        }else{
            throw new Exception("不支持此密文MODE解密");
        }
        byte[] plainBytes = cipher.doFinal(hexBytes);
        return plainBytes;
    }

    /**
     * 字节数组转16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制转字节数组
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }


    public static void main(String[] args) throws Exception {
        String key="123";
        System.out.println("明文长度"+"测试一下产固定测试一下产固定测试一下产固定".getBytes("UTF-8").length);
        System.out.println("ECB加密后："+encoder.encodeToString(jdkAES("测试一下产固定测试一下产固定测试一下产固定",createKey(key))));
        System.out.println("ECB解密后："+new String(decrypt(jdkAES("testAES",createKey(key)),createKey(key))));
        System.out.println("CBC模式加密："+ encoder.encodeToString(encryptCBC("测试一下产固定测试一下产固定测试一下产固定",getDefaultKey())));
        System.out.println("CBC模式解密："+ new String(decryptCBC("UTxlHu5cS4egnwKL5GFKlm5QayH0F1euXL1kAqXHec4eLvai7XL8akpGpd1LAErrTg4KvXqCYVrmuqlDlTpt5Q==",getDefaultKey(),BASE64_MODE)));
        System.out.println("CBC模式加密  16进制显示："+ parseByte2HexStr(encryptCBC("测试一下产固定测试一下产固定测试一下产固定",getDefaultKey())));
    }

}
