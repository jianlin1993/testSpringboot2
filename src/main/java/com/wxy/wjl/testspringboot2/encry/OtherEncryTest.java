package com.wxy.wjl.testspringboot2.encry;


import com.alibaba.druid.filter.config.ConfigTools;
import org.testng.annotations.Test;

@Test
public class OtherEncryTest {

    /**
     * druid解密数据库密码
     */
    @Test
    public void test1() throws Exception{
        String pwd = "cVQyf4hMmJTULeV5BRkMlngVjVIc56fUkz0L/p+lTw4P2kP8Mp7aUM9U/faqZEeMFJinkUQ6TYB9xL/3qFl0HQ==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ/R9TxeFGQuKNsGPBvhUCd6rXP2IASmLqzSBCzu0OWmFK1b8VqQQcg16T/15pJQB0x9wom6jrrwR3/hf6VAzFMCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
