package com.wxy.wjl.testng.dbUtils;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.Connection;

public class LoopSqlRunner {

    public LoopSqlRunner() throws Exception{
        runSqlUtils();
    }

    public static void runSqlUtils() throws Exception {
        Connection conn=null;
        try {
            DataSource dataSource = ConnDataSource.getDataSource();
            conn = dataSource.getConnection();
            ScriptRunner runner = new ScriptRunner(conn);
//          设置不自动提交
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.setSendFullScript(false);
            runner.setDelimiter(";");
            runner.setFullLineDelimiter(false);
            ResourcePatternResolver resolverDML = new PathMatchingResourcePatternResolver();
            ResourcePatternResolver resolverDDL = new PathMatchingResourcePatternResolver();
            Resource[] resourcesDML = resolverDML.getResources("classpath:run-sql/*/dml.sql");
            Resource[] resourcesDDL = resolverDDL.getResources("classpath:run-sql/*/ddl.sql");
            for(int i=0;i<resourcesDML.length;i++){
                runner.runScript(new InputStreamReader(resourcesDML[i].getInputStream(), "utf-8"));
                runner.runScript(new InputStreamReader(resourcesDDL[i].getInputStream(), "utf-8"));
                conn.commit();
            }
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }finally {
            close(conn);
        }
    }

    private static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            if (conn != null) {
                conn = null;
            }
        }
    }

}
