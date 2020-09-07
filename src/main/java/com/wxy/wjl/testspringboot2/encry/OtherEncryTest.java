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
        String pwd = "bzEYpWjhjk6qWBgk5JVDV1Hj0gdeqWV1r4Jcp4JR4DgGNiwfgB0XTlmF+KgoaeaX33YfXQPwh34k3cTQeplgZA==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIM3g+vNB5clTWdpfbWrJO9LsJo19XspYwuSYRHik45WMlHi/NZiwzp80GP8DhzK1O5yvlNo4BMVTAMduJCHG7UCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
