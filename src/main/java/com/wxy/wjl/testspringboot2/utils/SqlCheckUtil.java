package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * sql检查工具类
 */
public class SqlCheckUtil {

    private SqlCheckUtil(){};

    /**
     * 判断是否有where条件
     * @param visitor
     * @return
     */
    public static boolean hasWhereContion(SchemaStatVisitor visitor){
        return !StringUtils.equals("[]",String.valueOf(visitor.getConditions()));
    }

    public static void main(String[] args) {
        String sql = "TRUNCATE table a";
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = "";
        try{
            result= SQLUtils.format(sql, dbType);
        }catch (ParserException e){
            System.out.println(e.getCause());
        }

        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);
            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            stmt.accept(visitor);
            System.out.println("输出："+ JSON.toJSONString(visitor));
            System.out.println("检查是否有where条件："+ SqlCheckUtil.hasWhereContion(visitor));
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
        }
    }

}
