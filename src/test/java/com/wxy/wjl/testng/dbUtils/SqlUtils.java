package com.wxy.wjl.testng.dbUtils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class SqlUtils {


    public static void runSqlUtils () throws Exception{
        try{
            FileSystemResource fileSystemResource=new FileSystemResource("src/test/resources/sql/init.sql");
            EncodedResource encodedResource=new EncodedResource(fileSystemResource,"UTF-8");
            ScriptUtils.executeSqlScript(ConnDataSource.getDataSource().getConnection(),encodedResource);
        }catch (Exception e){
            System.out.println("sql脚本执行出错！");
            e.printStackTrace();
            //throw e;
        }


    }

}
