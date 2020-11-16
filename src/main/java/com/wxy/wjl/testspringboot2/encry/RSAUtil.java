package com.wxy.wjl.testspringboot2.encry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSAUtil {
    private static String cipherAlgorithm="RSA/ECB/PKCS1Padding";
    private static int reserveSize=11;
    private static int keyLength=2048;

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey) throws Exception {
        return decrypt(encryptedBytes,privateKey,keyLength,reserveSize,cipherAlgorithm);
    }

    public static byte[] encrypt(byte[] encryptedBytes,PublicKey publicKey) throws Exception {
        return encrypt(encryptedBytes,publicKey,keyLength,reserveSize,cipherAlgorithm);
    }


        public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength,int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;  
        int decryptBlockSize = keyByteSize - reserveSize;  
        int nBlock = encryptedBytes.length / keyByteSize;  
        ByteArrayOutputStream outbuf = null;  
        try {  
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
  
            outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);  
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {  
                int inputLen = encryptedBytes.length - offset;  
                if (inputLen > keyByteSize) {  
                    inputLen = keyByteSize;  
                }  
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);  
                outbuf.write(decryptedBlock);  
            }  
            outbuf.flush();
            return outbuf.toByteArray();  
        } catch (Exception e) {  
            throw new Exception("DEENCRYPT ERROR:", e);  
        } finally {  
            try{  
                if(outbuf != null){  
                    outbuf.close();  
                }  
            }catch (Exception e){  
                outbuf = null;  
                throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);  
            }  
        }  
    }


    public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;  
        int encryptBlockSize = keyByteSize - reserveSize;  
        int nBlock = plainBytes.length / encryptBlockSize;  
        if ((plainBytes.length % encryptBlockSize) != 0) {  
            nBlock += 1;  
        }  
        ByteArrayOutputStream outbuf = null;  
        try {  
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
  
            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);  
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {  
                int inputLen = plainBytes.length - offset;  
                if (inputLen > encryptBlockSize) {  
                    inputLen = encryptBlockSize;  
                }  
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);  
                outbuf.write(encryptedBlock);  
            }  
            outbuf.flush();  
            return outbuf.toByteArray();  
        } catch (Exception e) {  
            throw new Exception("ENCRYPT ERROR:", e);  
        } finally {  
            try{  
                if(outbuf != null){  
                    outbuf.close();  
                }  
            }catch (Exception e){  
                outbuf = null;  
                throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);  
            }  
        }  
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


    public static void main(String[] args) throws Exception{
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDWS5exUVCMabYZBp/5F9KQA6fil3J4WeKA9D5jbz0uf8pSapCOM0vLWm/4vQHcDHyR2apw026Z5wpkAuAWR4Kp8FbHPD5u5E2O+hAy2mrXNcb1ErCMjxTesjCLRMphsmr1lEoNrEfWPLgiMWRZGRbO7d46uYLGCXzQduNF4x1A/7uFPXHR91MlIw+TNJpcMSznH5vpzHtXENyy6+QTQgDa+sAjviUFWbTdRTw3OVWcp5imy9UcOuslsnndE3k23GhGfnyBx4Sbu20G9mbs97yMEPlDjaVELzB4Vij4uojKbYTFcfz55IGogJcbbmU6erP67eNMTM7m7tNSC2uTH8TDAgMBAAECggEAV4OPc26Nwon5P8cg1Y0RBJc5nKPUr6mlgpw8TkdDaRXu/jFez3PLZESOrboiZEcFKFsH7AOsi1V7PNSfxvl1NwlbpwnHRWoe8mDU9K+WKkVf9v2m9AAKuddzU6zIKZW/cIMaqvLMc4VgfTZGkMxaMC+cLcdMzKtbOZcTwZq6e6w0Tya36VjItT2TD1EiOUCCl+zbiyD08bPVbshIXxK2X7rmSMm1ZMU9dFYYDs73YgizyhV6LcTmzRoAo7j8FlRaNKGPWUI3LljkZljOdtVYM1E2sDfL41PiRhznvNL+uqIdRwgh05HghcKPjcUKf5xnpiFqIVEOiktQjhhc1v1AYQKBgQD/2R7ML/k3Oeb22HtRZBXKT4E46z4dmvdagb27xIulGjQYeKqjqQ3jwvXfwkLHlKF+1CFETnZJ9UK+YvHokIf6f2mVxNyDUPzg8WKHqrA+0+yJ+XG5ExmMY0HMq7Ut4sEI/bQsY9egirdxAP6bBHAO49472hgoME5JG1NZ2cm5/QKBgQDWbChfxM2JeidRf80NB9gWLqM2a9KpAXVNrIPoMHUIvjNLilNNfnnI0s5EWB/HTCoptmsDP2j9m2eav3tJkYzbk1FQigRKeofimSS30OCeRcTDwTCVCwSF880Givn+/PA+UrpTK+s/ZLDhFBRr7m2/C2t593loDJSTTmS1ymFVvwKBgENZgIX3yv8Cuw4YBr5yPlo3e6jkJEHe3WZx6ORY5PndYAFhImpawlFGrTruBG5NbgXZCbjVfsYRTjSYD15NTb5fdAf8/p+C3k7IvEtCX4rqdsYLaCh0IrGWH2gle1hM7MvVHWziC40zdv7EaEGedXE2AvZ2fw11SeZszxBECJpxAoGAZoIQjzBD3ryCPYkum1Mrr/cd2e+UJ23mCXs3GHLKEKL1XODFUF/tkt+M8sl6b8nuwkBOSqAu7kc0MZvnMdWelH6KOkmxIXYQrnjuP95g8+mA1uDt++Lnh651TvJz7742kGT2ZLqzXXwiv4fc93hmCRZJjGaOKPsSsJWiufIFtHsCgYBivwUUHDahCooZgiVbZiOxO9YwzeLSuEKJDUrUJ+GOWGw+9Ff49lsfx1NbwNgnIDO5th9UfM8jgARmIOQ940OpAEjrV8jO1n2Vameyxdakgj+Kl1SV1JhFcRADk5iQtFtrETRE3tNDYR4y6koUga6b6/TLdpvwSTm3LahPSQyYXw==";
        String password="B/oE0PQUvcAOPv9F317KuTIcz5X/IjL0xvwB1lvLP3PUqZrZhrZkjdPTPTGm2EZwwScwW5QBqHugi9/DPSZ27OiXq2tKqmQtr4VOfrlsgP4lMrC5Z/fY/fXzrW3cDoWYVftzaqZ9kTVJ8ZkPPdKxDzuop/FK0XLx/h73CtpJr6gw8HIcwj7aF5HF5PM8+Qz6aBuQEA22NDLa+JOBNegidFNxU8T6g0zt7/O0jOtQ4qrrXUBT9bJjEQYdAT5pulg7eEndhpI+VtL6nHG039vg0dxuoTVGqFaJuEukP/xtJPY4PqQmAoPHBepnUi/9S84FzErgotoyM4zReXavwnHTdw==";
        String value = new String(RSAUtil.decrypt(Base64.decodeBase64(password), getPrivateKey(privateKey)), StandardCharsets.UTF_8);
        System.out.println(value);
        int pwdLength = Integer.parseInt(value.substring(6, 8));
        System.out.println(pwdLength);
        value = value.substring(22, 22 + pwdLength);
        System.out.println(value);
    }

}