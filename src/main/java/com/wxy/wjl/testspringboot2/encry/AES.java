package com.wxy.wjl.testspringboot2.encry;



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


    //String转byte数组  编码
    private static final String CHARSET = "UTF-8";

    // 默认密钥 32bytes 256bits
    private static final String defaultKey = "1234567890ABCDEF1234567890ABCDEf";

    //偏移量字符串必须是16字节 当模式是CBC的时候必须设置偏移量
    private static final String iv = "0123456789ABCDEF";
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


    // 获取偏移量  CBC模式使用
    public static IvParameterSpec getIv() throws UnsupportedEncodingException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        return ivParameterSpec;
    }

    // CBC模式加密
    public static byte[] encrypt(String src, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
        return cipherBytes;
    }
    // CBC模式解密
    public static byte[] decrypt(String src, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);

        IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] hexBytes = decoder.decode(src);
        byte[] plainBytes = cipher.doFinal(hexBytes);
        return plainBytes;
    }


    public static void main(String[] args) throws Exception {
        String key="123";
        System.out.println("加密后："+new String(encoder.encode(jdkAES("testAES",createKey(key)))));
        System.out.println("解密后："+new String(decrypt(jdkAES("testAES",createKey(key)),createKey(key))));
        System.out.println("CBC模式加密："+ new String(encoder.encode(encrypt("aaaaaaaaaaa",defaultKey.getBytes("utf-8")))));
        System.out.println("CBC模式解密："+ new String(decrypt("+SgJoPxO5HugIVnaw9D5qg==",defaultKey.getBytes("utf-8"))));
    }

}
