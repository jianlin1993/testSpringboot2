package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * jdbc处理BLOB字段  字节流字段
 */
@Service
public class JdbcUtils {

    @Autowired
    Environment env;

    public  Connection getConnection() throws Exception {
        HashMap<String,String> dsInfMap=getConnPar();

        Class.forName(dsInfMap.get("driverClassName"));
        return DriverManager.getConnection(dsInfMap.get("url"), dsInfMap.get("userName"), dsInfMap.get("passWord"));
    }

    private  HashMap<String,String> getConnPar() throws Exception{
        HashMap<String,String> dsInfMap=new HashMap<>();
        String url = env.getProperty("ecp.datasource.ora1.url");
        String userName = env.getProperty("ecp.datasource.ora1.username");
        String passWord = env.getProperty("ecp.datasource.ora1.password");
        String driverClassName = env.getProperty("ecp.datasource.ora1.driverClassName");
        String publicKey = env.getProperty("ecp.datasource.ora1.publicKey");
        if(StringUtils.isNotBlank(publicKey)){
            passWord = ConfigTools.decrypt(publicKey, passWord);
        }
        dsInfMap.put("url",url);
        dsInfMap.put("userName",userName);
        dsInfMap.put("passWord",passWord);
        dsInfMap.put("driverClassName",driverClassName);
        return dsInfMap;
    }


    public  String queryBlobData(String sql) throws Exception {
        String data = "";
        Connection connection = getConnection();

        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(sql);
            if(rs.next()){
                Blob blob=rs.getBlob(1);
                data = new String(blob.getBytes(1, (int) blob.length()),"UTF-8");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        } finally {
            close(rs);
            close(statement);
            close(connection);
        }
        return data;
    }

    public void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception var2) {
            }
        }

    }
}
