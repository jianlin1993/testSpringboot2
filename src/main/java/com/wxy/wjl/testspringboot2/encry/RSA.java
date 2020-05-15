package com.wxy.wjl.testspringboot2.encry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA 工具类
 * 分段加密
 *
 * 公钥加密，私钥签名
 *
 */

public class RSA {

    /**
     * 正确RSA加密
     */

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名  可选模式有很多种MD5withRSA、SHA256withRSA
     *
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = getKeyPair();
//            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
//            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));

            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIbZrRQ2FvclQ4sf44R6EhjtHujCjhxNGCl+a6OTZlXF0jmbze50hPKCSoWNqe0X5k6EOoibVJalEx/zYDdnZ5pZG9LazYobzjje5pSLb8AhUx5qQVscn0E8Rl+0Fr1rhHz9x4oDmNMyXFgRyyMT71oE61ZtqwrFcci6MpQMv9xBAgMBAAECgYAZqaM+jWyha/4Go/Lwayq2g6m2MhBJYoKOovhpEMwQ1qzDOdte3d3PKOx1rhgAfuPdXVymUqkuYgvZsL9Gy+7zMGdAeZdIzS+OlPAvKFUAsOpTDnBYUkgRvypEk98Ujly4Ih0sq3ijADXNLzlGwvZJBRbIeaqU00Wr2lfMqX/8YQJBAOVaW42kkQTWclEx8Kj0/udYVLTE1M2XhpQdmtL7+PgdE65jdtEht4eHPA5jZC6pWvINmejpM1Z365iLSGNHtPMCQQCWhIbiRASoHsecWG995iAubFaVZWWT00OfNaom0VuTEePVRorkMYPsAanJJKBEFPsQPi0n6sKfh8HfZxxX7Ub7AkAG9fATY2w3Tl7X0gEwz4aw9MBVYCfpOXEPK7rYLmeG+DLxtvkTLcfJKDSNZzzSIbet4vXJ2NIDDM6sqYV/5dw7AkA+y7+yeoou0diZg9PQBMh8VbqxzYHZcJSPwcXhOAsqipYxDqM3nOja976sKlmqzc4I8sfIFYtpHyxlVhFfJaEtAkBrrMxiVg5a8OXt4fwS1t0CJeq9sDZCWYM9wgYIHpDohySe6NqLmgcCFln0afMq4vr58C2Bu+xA2nBMtswDfs2p";
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCG2a0UNhb3JUOLH+OEehIY7R7owo4cTRgpfmujk2ZVxdI5m83udITygkqFjantF+ZOhDqIm1SWpRMf82A3Z2eaWRvS2s2KG8443uaUi2/AIVMeakFbHJ9BPEZftBa9a4R8/ceKA5jTMlxYEcsjE+9aBOtWbasKxXHIujKUDL/cQQIDAQAB";
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
            // RSA加密
            String data = "{\"businessType\":\"M015\",\"cardNo\":\"6217****9247\",\"crpIdNo\":\"3701****5349\",\"drawAmt\":\"29.82\",\"mobilePhone\":\"15665751111\",\"organizationNumber\":\"bjlx\",\"psamNo\":\"6217****9247\",\"rate\":\"0.0053\",\"sn\":\"10002701110011\",\"transAmt\":\"30.00\",\"transTime\":\"2019-12-30 15:36:09\",\"transactionNo\":\"19123015360111164100\"}";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);

            // RSA签名
            String sign = sign(data, getPrivateKey(privateKey));
            // RSA验签
            boolean result = verify(data, getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
