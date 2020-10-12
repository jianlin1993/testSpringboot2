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
        String pwd = "TkD1Ofxytvo7WcDcpervKaD00QqcOyyUx6eY4jw+TF3v2Nod4EpiqC7L4KGA7lkhLeoeX4r5uvPoWmrnZ1M3UA==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKkb17c8c7ovSlmjRIMQ8P0T71EU5RXzyv0o2A1tOh+UnuGETaMA3ibfMwq29B2cy7VE5b+7t5TCJ/FAS60eTPsCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
