package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.io.IOUtils.copy;

/**
 * jdbc静态工具类
 */
public class JdbcUtils {

    public static void main(String[] args) throws Exception{
        String sql="SELECT BYTS FROM BSDBUI.T_WKF_GEBYTE where ID = '5465323'";
        String result=queryBlobData(sql);
        System.out.println(result);
    }


    public static Connection getConnection() throws Exception {
        HashMap<String,String> dsInfMap=getConnPar();

        Class.forName(dsInfMap.get("driverClassName"));
        return DriverManager.getConnection(dsInfMap.get("url"), dsInfMap.get("userName"), dsInfMap.get("passWord"));
    }

    private static HashMap<String,String> getConnPar() throws Exception{
        HashMap<String,String> dsInfMap=new HashMap<>();
        String url = "jdbc:oracle:thin:@10.0.0.6:1521/cba";
        String userName = "BSDBUI";
        String passWord = "BSDBUI";
        String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String publicKey = "";
        if(StringUtils.isNotBlank(publicKey)){
            passWord = ConfigTools.decrypt(publicKey, passWord);
        }
        dsInfMap.put("url",url);
        dsInfMap.put("userName",userName);
        dsInfMap.put("passWord",passWord);
        dsInfMap.put("driverClassName",driverClassName);
        return dsInfMap;
    }


    public static String queryBlobData(String sql) throws Exception {
        String data = "";
        String head="";
        Connection connection = getConnection();

        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(sql);
            if(rs.next()){
                Blob blob=rs.getBlob(1);
               // data=streamToString(new BOMInputStream(blob.getBinaryStream()),StandardCharsets.UTF_8);
                //head = new String(blob.getBytes(1, 7),StandardCharsets.UTF_16LE);
                data = new String(blob.getBytes(8, (int) blob.length()),StandardCharsets.UTF_8);
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

    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception var2) {
            }
        }

    }
    public static String streamToString(InputStream input, Charset encoding) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        Throwable var3 = null;

        String var4;
        try {
            copy((InputStream)input, (Writer)sw, (Charset)encoding);
            var4 = sw.toString();
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (sw != null) {
                if (var3 != null) {
                    try {
                        sw.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    sw.close();
                }
            }

        }

        return var4;
    }
}
