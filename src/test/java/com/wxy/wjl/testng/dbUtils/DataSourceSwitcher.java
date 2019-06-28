package com.wxy.wjl.testng.dbUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceSwitcher {
    private Map<String, DataSource> dataSourceMap=new HashMap<>();
    private DataSource defaultDataSource;
    private static final Logger logger= LoggerFactory.getLogger(DataSourceSwitcher.class);
    private static final ThreadLocal<String> dataSourceKey=new ThreadLocal();

    public DataSourceSwitcher(){}

    public void clean(){
        logger.debug("thread:{},remove,datasource:{}",Thread.currentThread().getName(),dataSourceKey.get());
        dataSourceKey.remove();
    }

    public void set(String dataSourceId){
        logger.debug("Thread:{},set,datasource:{}",Thread.currentThread().getName(),dataSourceId);
        dataSourceKey.set(dataSourceId);
    }

    public void put(String key,String url){
        DataSource dataSource=null;
        if(url ==null || url.length() <1){
            System.out.println("数据库初始化字符串或者文件路径不能为空！");
        }
        dataSource=ConnDataSource.uriDataSource(url);
        this.dataSourceMap.put(key,dataSource);
    }


    public static DataSourceSwitcher getDataSourceSwitcher(String xmlPath){
        ApplicationContext applicationContext=new FileSystemXmlApplicationContext(xmlPath);
        return (DataSourceSwitcher) applicationContext.getBean(DataSourceSwitcher.class);
    }

    public DataSource getCurrentDataSource(){
        String dataSourceId=dataSourceKey.get();
        if(dataSourceId != null && !this.dataSourceMap.isEmpty()){
            DataSource dataSource=this.dataSourceMap.get(dataSourceId);
            return dataSource;
        }else {
            return this.defaultDataSource;
        }
    }


    public Map<String,DataSource> getDataSourceMap(){
        return this.dataSourceMap;
    }

    public void setDataSourceMap(Map<String,DataSource> dataSourceMap){
        this.dataSourceMap=dataSourceMap;
    }


    public DataSource getDefaultDataSource(){
        return this.defaultDataSource;
    }

    public void setDefaultDataSource(DataSource defaultDataSource){
        this.defaultDataSource=defaultDataSource;
    }


}
