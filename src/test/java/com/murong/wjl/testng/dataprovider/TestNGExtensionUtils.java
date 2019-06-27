package com.murong.wjl.testng.dataprovider;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;

public class TestNGExtensionUtils {
    public static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;


    public TestNGExtensionUtils(){}

    public static SqlSessionFactory getSession(){return sqlSessionFactory;}


    public static String ReadFile(String path){
        BufferedReader reader=null;
        String laster="";

        try{
            FileInputStream fileInputStream=new FileInputStream(path);
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
            reader=new BufferedReader(inputStreamReader);
            for(String tempString=null;(tempString = reader.readLine()) != null;laster=laster+tempString){
                ;
            }
            reader.close();
        }catch (IOException var14){
            var14.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException var13){
                    var13.printStackTrace();
                }
            }
        }
        return laster;
    }

}
