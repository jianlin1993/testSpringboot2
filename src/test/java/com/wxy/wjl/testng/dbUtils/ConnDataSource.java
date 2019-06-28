package com.wxy.wjl.testng.dbUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnDataSource {
    private static DataSource ds=null;

    public static DataSource getDataSource(){return ds;}

    public static void setDataSource(DataSource ds){ds=ds;}

    public static DataSource uriDataSource(String uri){
        try{
            ds=new DriverManagerDataSource(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ds;
    }

    public static DataSource xmlDataSource(String xmlPath){
        ApplicationContext applicationContext=new FileSystemXmlApplicationContext(xmlPath);
        applicationContext.getBean("dataSource");
        try{
            ds=(DataSource)applicationContext.getBean("dataSource");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ds;
    }

    public static void release(Connection conn, Statement st, ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            rs=null;
        }
        if(st != null){
            try{
                st.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            st=null;
        }
        if(conn != null){
            try{
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            conn=null;
        }




    }


}
