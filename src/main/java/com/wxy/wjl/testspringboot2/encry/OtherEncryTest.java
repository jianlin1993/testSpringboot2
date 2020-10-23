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
        String pwd = "YAa/Z1OThCzDbWpW8kqXHyCGVbPlmRQCsqOGdHBIv63qLZ5wWu6X3bbVNm+BU0p4lAbZQ0yqsiuQIb+o9bzyZg==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJLHT0jbKmrfZ6x4UCJAjyY5EhPM86kxcHAT4nwcvnZ8XD10MJdSF8wFu+w9hicUv8P1b3mJdJNSitJJRBO3AtkCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
