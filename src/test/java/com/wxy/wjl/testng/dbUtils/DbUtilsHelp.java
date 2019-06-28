package com.wxy.wjl.testng.dbUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbUtilsHelp {

    private static final Log logger= LogFactory.getLog(DbUtilsHelp.class);
    private DataSource dataSource;
    private QueryRunner queryRunner;
    private DataSourceSwitcher dataSourceSwitcher;

    public DbUtilsHelp(){
    }

    public DbUtilsHelp(ConnDataSource dataSource){
        this.dataSource=ConnDataSource.getDataSource();
    }

    public void setDataSource(String uri){
        if(uri == null || uri.length() <1){
            System.out.println("数据库初始化字符串或文件路径不能为空！");
        }
        if(!uri.toLowerCase().contains("odbc") && !uri.contains("jdbc")){
            this.dataSource=ConnDataSource.xmlDataSource(uri);
        }else{
            this.dataSource=ConnDataSource.uriDataSource(uri);
        }
    }

    public DbUtilsHelp(DataSourceSwitcher dataSourceSwitcher){
        this.dataSourceSwitcher=dataSourceSwitcher;
    }

    public void setDataSourceSwitcher(DataSourceSwitcher dataSourceSwitcher){
        this.dataSourceSwitcher=dataSourceSwitcher;
    }

    private DataSource getDataSource(){
        return this.dataSourceSwitcher == null?ConnDataSource.getDataSource() :this.dataSourceSwitcher.getCurrentDataSource();
    }



    /**
     * 查询多行记录
     * @param sql
     * @return
     */
    public List<Map<String,Object>> selectList(String sql){
        return this.selectList(sql,null);
    }
    public List<Map<String,Object>> selectList(String sql,Object param){
        return this.selectList(sql,new Object[]{param});
    }
    public List<Map<String,Object>> selectList(String sql,Object[] params){
        this.queryRunner=new QueryRunner(this.getDataSource());
        Object list=new ArrayList<>();
        try {
            if(params == null){
                list=this.queryRunner.query(sql,new MapListHandler());
            }else{
                list=this.queryRunner.query(sql,new MapListHandler(),params);
            }
        }catch (SQLException e){
            logger.error("执行selectList发生错误：",e);
        }
        return (List)list;
    }


    /**
     * 查询一行记录
     * @param sql
     * @return
     */
    public Map<String,Object> selectOne(String sql){
        return this.selectOne(sql,null);
    }
    public Map<String,Object> selectOne(String sql,Object param){
        return this.selectOne(sql,new Object[]{param});
    }
    public Map<String,Object> selectOne(String sql,Object[] params){
        this.queryRunner=new QueryRunner(this.getDataSource());
        Map map=null;
        try {
            if(params == null){
                map=this.queryRunner.query(sql,new MapHandler());
            }else{
                map=this.queryRunner.query(sql,new MapHandler(),params);
            }
        }catch (SQLException e){
            logger.error("执行selectOne发生错误：",e);
        }
        return map;
    }


    /**
     * 查询一个子段的值
     * @param sql
     * @param columnName
     * @return
     */
    public Object selectCol(String sql,String columnName){
        return this.selectCol(sql,columnName,null);
    }
    public Object selectCol(String sql,String columnName,Object param){
        return this.selectCol(sql,columnName,new Object[]{param});
    }
    public Object selectCol(String sql,String columnName,Object[] params){
        this.queryRunner=new QueryRunner(this.getDataSource());
        Map map=null;
        try {
            if(params == null){
                map=this.queryRunner.query(sql,new ScalarHandler<>(columnName));
            }else{
                map=this.queryRunner.query(sql,new ScalarHandler<>(columnName),params);
            }
        }catch (SQLException e){
            logger.error("执行selectCol发生错误：",e);
        }
        return map;
    }

    /**
     * 更新
     * @param sql
     * @return
     */
    public int update(String sql){
        return this.update(sql,(Object[])null);
    }
    public int update(String sql,Object param){
        return this.update(sql,new Object[]{param});
    }

    public int update(String sql,Object[] params){
        this.queryRunner=new QueryRunner(this.getDataSource());
        int affectedRows=0;
        try {
            if(params == null){
                affectedRows=this.queryRunner.update(sql);
            }else{
                affectedRows=this.queryRunner.update(sql,params);
            }
        }catch (SQLException e){
            logger.error("执行update发生错误：",e);
        }
        return affectedRows;
    }

    /**
     * 插入记录
     * @param sql
     * @return
     */
    public int insert(String sql){
        return this.insert(sql,null);
    }
    public int insert(String sql,Object param){
        return this.insert(sql,new Object[]{param});
    }

    public int insert(String sql,Object[] params){
        this.queryRunner=new QueryRunner(this.getDataSource());
        int affectedRows=0;
        try {
            if(params == null){
                affectedRows=this.queryRunner.insert(sql,new ScalarHandler<>());
            }else{
                affectedRows=this.queryRunner.insert(sql,new ScalarHandler<>(),params);
            }
        }catch (SQLException e){
            logger.error("执行insert发生错误：",e);
        }
        return affectedRows;
    }

    /**
     * 删除
     * @param sql
     * @return
     */
    public int delete(String sql){
        this.queryRunner=new QueryRunner(this.getDataSource());
        int affectedRows=0;
        try {
                affectedRows=this.queryRunner.update(sql);
        }catch (SQLException e){
            logger.error("执行delete发生错误：",e);
        }
        return affectedRows;
    }


}
