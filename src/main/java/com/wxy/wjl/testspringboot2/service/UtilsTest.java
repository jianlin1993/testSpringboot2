package com.wxy.wjl.testspringboot2.service;

import com.alibaba.druid.filter.config.ConfigTools;
import org.testng.annotations.Test;

public class UtilsTest {

    /**
     * druid解密数据库密码
     */
    @Test
    public void druidDecrypt() throws Exception{
        String pwd = "d1VeV9B7tWfVB1EFAGQKFkD8gNyVAgQqvUvy+N8c7eG1wMUBqmwypJAtceU1tMkhIgi4xCNv3WP2Ptz6Fd/Vjg==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJEOiy0Vf996paS13nuLz4scR/1dbZgg0fEYgzb+o94fEjpP+9Ipp1IStxFnvFOmZcAUYXEOq1s/JEO4dgtfF3kCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println(depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
