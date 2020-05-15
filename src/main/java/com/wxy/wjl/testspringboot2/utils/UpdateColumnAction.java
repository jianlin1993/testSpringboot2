package com.wxy.wjl.testspringboot2.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成库中所有表的更新语句（根据规则更新某个字段）
 */
public class UpdateColumnAction {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String URL = "jdbc:oracle:thin:@//10.0.0.6:1521/cba";
        String USER = "CSDACM";
        String PASSWORD = "CSDACM";
        Class.forName("oracle.jdbc.OracleDriver");
        // 2.获得数据库链接
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assert conn != null;
        Map<String, String> map = queryTable(conn);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sql(conn, entry.getKey());
        }
    }

    private static Map<String, String> queryTable(Connection conn) throws SQLException {

        // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        //预编译
        String sql = "select table_name from user_tables";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        // 4.处理数据库的返回结果(使用ResultSet类)
        Map<String, String> map = new HashMap<>();
        while (rs.next()) {
            map.put(rs.getString("TABLE_NAME"), rs.getString("TABLE_NAME"));
        }
        return map;
    }

    private static void sql(Connection conn, String tableName) {
        try {
            System.out.println("--表名：" + tableName);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            //预编译
            String sql = "select * from user_col_comments where table_name = upper('" + tableName + "') ";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            // String updSql = "update t_bui_audcoldtl set col_dsc=? where bus_cd=? and col_nm=?";
            while (rs.next()) {
                StringBuilder updSql = new StringBuilder("update t_bui_audcoldtl set col_dsc=");
                //System.out.println(rs.getString("COLUMN_NAME") + " " + rs.getString("COMMENTS"));
                String comments = rs.getString("COMMENTS");
                String columnName = rs.getString("COLUMN_NAME");
                if (columnName != null && StringUtils.contains(comments, "|")) {
                    updSql.append("'").append(comments.split("\\|")[1]).append("'");
                } else {
                    updSql.append("' '");
                }
                updSql.append(" where bus_cd='").append(tableName);
                updSql.append("' and col_nm='").append(columnName).append("';");
                System.out.println(updSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
