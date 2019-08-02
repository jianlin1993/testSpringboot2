package com.wxy.wjl.testng.dbUtils;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

public class SqlRunner {

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
			runner.runScript(new InputStreamReader(new FileInputStream("src/test/resources/sql/init.sql"), "utf-8"));
			conn.commit();
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