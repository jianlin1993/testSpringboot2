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
        String pwd = "kd0dSZVmBqQ+AwnypsTzLIMapzrjpC5NEhDC4t6OG1OicwQ43GbpANBYeHNC/fabOpHf/OS6mVHX7rEM0at/aw==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJX/Z14549D3dZa+CHTJR1cUJVGXUZOailgAL/xiUFZmgtsCvN824fEAv25/3CkzB35AchB5J4D4q+nIzbeHr98CAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
