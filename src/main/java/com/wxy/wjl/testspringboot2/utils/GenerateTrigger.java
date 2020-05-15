package com.wxy.wjl.testspringboot2.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

/**
 * 根据表明生成sql语句触发器
 *
 * @version 5.0.0
 * created  at 2020-02-15 14:57
 */
public class GenerateTrigger {
	static PrintWriter logger;

	/**
	 * 授权
	 * grant create any trigger to kafkacap;
	 * <p>
	 * 公共序列
	 * DROP SEQUENCE AUD_SEQUENCE;
	 * CREATE SEQUENCE AUD_SEQUENCE
	 * INCREMENT BY 1
	 * START WITH 1
	 * MAXVALUE  999999999999
	 * CYCLE
	 * CACHE 1000;
	 * <p>
	 * 公共字段
	 * AUD_ID NUMBER NOT NULL,
	 * SYS_TIMESTAMP TIMESTAMP DEFAULT SYSTIMESTAMP,
	 *
	 */

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@//10.0.0.6:1521/cba";
		String username = "LOGADM";
		String password = "LOGADM";

//		String url = "jdbc:oracle:thin:@//172.16.0.100:1521/dupcba";
//		String username = "LOGADM";
//		String password = "8e6M58511268e111";
		Properties info = new Properties();
		info.put("user", username);
		info.put("password", password);

		return DriverManager.getConnection(url, info);
	}

	public static void main(String[] args) throws Exception {

		logger = new PrintWriter("init.sql");

		String[] tables = new String[]{

				"BSDBUI.T_BUI_OPLG"

		};

		log("-- 创建用户");
		log("-- create tablespace TBS_SOMADS_DATA datafile '/u01/app/oracle/oradata/mslver/TBS_NCBA_DATA.dbf' size 10m autoextend on next 10m maxsize unlimited extent management local uniform size 8m;");

		log("-- 授权(需要dba权限执行该授权）");
		log("-- grant create trigger to SOMADS;");
		for (String tableName : tables) {
			log("-- grant select on  "+tableName+" to SOMADS;");
		}

		log("-- 序列");
		log("DROP SEQUENCE SOMADS.AUD_SEQUENCE;\n" +
				"CREATE SEQUENCE SOMADS.AUD_SEQUENCE\n" +
				"INCREMENT BY 1\n" +
				"START WITH 1\n" +
				"MAXVALUE  999999999999\n" +
				"CYCLE\n" +
				"CACHE 1000;");



		for (String tableName : tables) {
			System.out.println("table:" + tableName);
			log("--  " + tableName);
			process(tableName);
		}
		logger.close();

	}

	public static void log(String msg) throws IOException {
		logger.println(msg);
		logger.flush();
	}

	public static void process(String tableName) throws Exception {

		String tableName1 = tableName;
		int idx = tableName.indexOf(".");
		if (idx != -1) {
			tableName1 = tableName.substring(idx + 1);
		}
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("select * from " + tableName);

		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();


//		System.out.println("DROP SEQUENCE " + tableName1 + "_SEQUENCE;    \n" +
//				"CREATE SEQUENCE " + tableName1 + "_SEQUENCE    \n" +
//				"INCREMENT BY 1 \n" +
//				"START WITH 1\n" +
//				"MAXVALUE  999999999999\n" +
//				"CYCLE \n" +
//				"CACHE 1000; \n");

		StringBuffer stringNameBuffer = new StringBuffer();
		StringBuffer stringValueBuffer = new StringBuffer();

		stringNameBuffer.append("AUD_ID,");
		stringValueBuffer.append("to_char(sysdate,'YYYYMMDD') || lpad(AUD_SEQUENCE.NEXTVAL, 12, '0'),");

		StringBuffer buf = new StringBuffer();
		buf.append("DROP TABLE SOMADS." + tableName1 + "_AUD;\n");
		buf.append("CREATE TABLE SOMADS." + tableName1 + "_AUD(\n");
		buf.append("    AUD_ID NUMBER NOT NULL,\n");
		buf.append("    SYS_TIMESTAMP TIMESTAMP DEFAULT SYSTIMESTAMP,\n");
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String name = rsmd.getColumnName(i);
			StringBuffer colBuf = new StringBuffer();
			StringBuffer oldColBuf = new StringBuffer();

			oldColBuf.append(name + "_OLD");
			colBuf.append(name);

			int type = rsmd.getColumnType(i);
			stringNameBuffer.append(name + ",");
			stringNameBuffer.append(name + "_OLD,");
			stringValueBuffer.append(":new." + name + ",");
			stringValueBuffer.append(":old." + name + ",");
			switch (type) {
				case Types.VARCHAR:
					colBuf.append("  VARCHAR2");
					colBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");

					oldColBuf.append("  VARCHAR2");
					oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");

					break;
				case Types.CHAR:
					colBuf.append("  CHAR");
					colBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");

					oldColBuf.append("  CHAR");
					oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");

					break;
				case Types.NUMERIC:
					colBuf.append("  NUMBER");
					if (rsmd.getColumnDisplaySize(i) != 39 ) {
						 if (rsmd.getScale(i) != 0) {
							colBuf.append("(" + rsmd.getColumnDisplaySize(i) + "," + rsmd.getScale(i) + ")");
						} else {
							colBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");
						}
					}

					oldColBuf.append("  NUMBER");
					if (rsmd.getColumnDisplaySize(i) != 39 ) {
						if (rsmd.getScale(i) != 0) {
							oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + "," + rsmd.getScale(i) + ")");
						} else {
							oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");
						}
					}
					break;
				case Types.DECIMAL:
					colBuf.append("  DECIMAIL");
					if (rsmd.getScale(i) != 0) {
						colBuf.append("(" + rsmd.getColumnDisplaySize(i) + "," + rsmd.getScale(i) + ")");
					} else {
						colBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");
					}

					oldColBuf.append("  DECIMAIL");
					if (rsmd.getScale(i) != 0) {
						oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + "," + rsmd.getScale(i) + ")");
					} else {
						oldColBuf.append("(" + rsmd.getColumnDisplaySize(i) + ")");
					}

					break;
				default:
					throw new IllegalArgumentException("not found type:" + type);
			}
			buf.append("    " + oldColBuf + ",\n");
			buf.append("    " + colBuf + ",\n");

		}
		buf.append("    PRIMARY KEY(AUD_ID)");
		buf.append("\n );\n");
		log(buf.toString());

		stringNameBuffer.deleteCharAt(stringNameBuffer.length() - 1);
		stringValueBuffer.deleteCharAt(stringValueBuffer.length() - 1);
		if(stringNameBuffer.length()>4000){
			int i = stringNameBuffer.indexOf(",", 4000);
			stringNameBuffer.insert(i+1,"\n\t");
		}

		if(stringValueBuffer.length()>4000){
			int i = stringValueBuffer.indexOf(",", 4000);
			stringValueBuffer.insert(i+1,"\n\t");
		}
		StringBuffer triggerBuf = new StringBuffer();
		triggerBuf.append("CREATE OR REPLACE TRIGGER SOMADS.TRIGGER_" + tableName1 + "\n" +
				"   AFTER INSERT or UPDATE or DELETE\n" +
				"   ON " + tableName + "\n" +
				"   FOR EACH ROW\n" +
				"BEGIN\n" +
				" insert into " + tableName1 + "_AUD(" + stringNameBuffer + ")\n values(" + stringValueBuffer + ");\n" +
				"END;\n/");

		log(triggerBuf.toString());

		rs.close();
		ps.close();
		connection.close();

	}


}
