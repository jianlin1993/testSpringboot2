package com.wxy.wjl.testspringboot2.sqlcheck.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.wxy.wjl.testspringboot2.sqlcheck.constants.SqlConstants;
import com.wxy.wjl.testspringboot2.sqlcheck.enums.SqlTypeEnum;
import com.wxy.wjl.testspringboot2.sqlcheck.model.Column;
import com.wxy.wjl.testspringboot2.sqlcheck.model.Index;
import com.wxy.wjl.testspringboot2.sqlcheck.model.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * sql检查工具类
 */
public class SqlCheckUtil {

    private SqlCheckUtil(){};


    /**
     * 检查sql类型是否合法
     * @param sqlType
     * @return
     */
    public static boolean checkSqlType(String sqlType){
        return StringUtils.isNotBlank(SqlTypeEnum.getDescByvalue(sqlType));
    }

    /**
     * 判断是否有where条件
     * @param visitor
     * @return
     */
    public static boolean hasWhereContion(SchemaStatVisitor visitor){
        return !StringUtils.equals("[]",String.valueOf(visitor.getConditions()));
    }

    public static void main(String[] args) {
        String sql="Alter Table  \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\" Add CHG_ADDR_FLAG number NOT NULL;\n" +
                "comment ON COLUMN \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\".\"CHG_ADDR_FLAG \" IS 'OPER_NO|请求id';";

//        sqlComment();
        //publicFieldCheck();
        String formatSql= SQLUtils.format(sql, "oracle");
        System.out.println(formatSql);
        List<Column> sqlColumnList=new ArrayList<>();
        List<Index> sqlIndexList=new ArrayList<>();
        List<String> dropSqlList=new ArrayList<>();
        SqlCheckUtil.alterTableSqlParse(formatSql,sqlColumnList,sqlIndexList,dropSqlList);
       // System.out.println(createTableSqlParse(formatSql));
    }


    /**
     * 测试使用
     */
    public static void sqlComment(){
        String sql="COMMENT ON TABLE \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\" IS 'SQL审核历史表';\n" +
                "COMMENT ON COLUMN \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\".\"ID\" IS '主键';\n" +
                "COMMENT ON COLUMN \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\".\"OPER_NO\" IS '操作员编号';";
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

            SQLCommentStatement stmt = (SQLCommentStatement)stmtList.get(i);
            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            stmt.accept(visitor);
            SQLCharExpr sqlCharExpr=(SQLCharExpr)stmt.getComment();

            System.out.println("备注类型："+ JSON.toJSONString(stmt.getType()));
            System.out.println("字段名："+stmt.getOn().getName());
            System.out.println("备注："+ JSON.toJSONString(sqlCharExpr.getValue()));
            // System.out.println("stmt.getName()"+JSON.toJSONString(stmt.getName().getSimpleName()));
            //System.out.println("输出："+ JSON.toJSONString(visitor));
//            //获取操作方法名称,依赖于表名称
//            System.out.println("Manipulation : " + visitor.getTables());
//            //获取字段名称
//            System.out.println("fields : " + visitor.getColumns());
        }
    }

    /**
     * 测试使用
     */
    public static void publicFieldCheck() {
        String sql ="alter table \"DEVOPS\".\"T_BUI_SQLCHECK_HIS\" modify  OPER_NO varchar2(120);";
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = "";
        try {
            result = SQLUtils.format(sql, dbType);
        } catch (ParserException e) {
            System.out.println(e.getCause());
        }
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement statement =stmtList.get(i);
            System.out.println("statement: "+statement);
            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            statement.accept(visitor);
            System.out.println("visitor：" + JSON.toJSONString(visitor));
            System.out.println("Children :"+statement.getChildren());
            Collection<TableStat.Column> columns = visitor.getColumns();
            Iterator<TableStat.Column> iterator = columns.iterator();
            while (iterator.hasNext()) {
                System.out.println("字段：" + JSON.toJSONString(iterator.next()));
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    /**
     * DDL 修改  sql解析
     * @param sql
     * @param sqlColumnList
     * @param sqlIndexList
     * @param dropSqlList
     */
    public static void alterTableSqlParse(String sql,List<Column> sqlColumnList,List<Index> sqlIndexList ,List<String> dropSqlList) {
        String dbType = JdbcConstants.ORACLE;
        //字段注释map<字段名称，注释>
        Map<String,String> colunmCmmentMap = new HashMap<>(16);
        //开始解析
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        String tableName = "";
        String operType = "";
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement statement = stmtList.get(i);
            OracleSchemaStatVisitor oracleSchemaStatVisitor = new OracleSchemaStatVisitor();
            statement.accept(oracleSchemaStatVisitor);
            //System.out.println("statement"+JSON.toJSONString(statement.toString()));
            if(statement.toString().indexOf("DROP")==0){
                dropSqlList.add(statement.toString());
                continue;
            }else if(statement.toString().indexOf("COMMENT")==0){
                //备注判断放在前面,因为此操作不能解析出operType字段 备注字段名此处使用schema.table.column
                SQLCommentStatement stmt = (SQLCommentStatement)statement;
                SQLCharExpr sqlCharExpr=(SQLCharExpr)stmt.getComment();
                String columnName = stmt.getOn().getName().toString().replaceAll("\"","");
                String commentText = sqlCharExpr.getText();
                colunmCmmentMap.put(columnName,commentText);
                continue;
            }
            //包含了schema
            tableName= oracleSchemaStatVisitor.getTables().keySet().iterator().next().toString();
          //  System.out.println("表名："+tableName);
            operType = oracleSchemaStatVisitor.getTableStat(tableName).toString();
           // System.out.println("操作类型："+operType);
            if(operType.contains("Index")){
                Index index = new Index();
                String indexName ="";
                String tablespaceName = "";
                StringBuffer columnName = new StringBuffer();
                Collection<TableStat.Column> columns = oracleSchemaStatVisitor.getColumns();
                //不能使用parallelStream()异步遍历，此种遍历方式会导致顺序变化
                //columns.parallelStream().forEach(column->{columnName.append(JSON.parseObject(JSON.toJSONString(column), Feature.OrderedField).get("name")+",");});
                Iterator<TableStat.Column> columnIterator=columns.iterator();
                while(columnIterator.hasNext()){
                    columnName.append(JSON.parseObject(JSON.toJSONString(columnIterator.next()), Feature.OrderedField).get("name")+",");
                }
                JSONObject indexJson = JSON.parseObject(JSON.toJSONString(oracleSchemaStatVisitor.getColumns().iterator().next()));
                boolean isPrimaryKey = indexJson.getBoolean("primaryKey");
                String isUnique = SqlConstants.STRING_N;
                if(isPrimaryKey){
                    //创建主键 解析json获取 主键名称
                    List<SQLObject> children = statement.getChildren();
                    for (SQLObject child : children) {
                        JSONObject childJsonObject = JSON.parseObject(JSON.toJSONString(child));
                        if(childJsonObject.containsKey("constraint")){
                            indexName = childJsonObject.getJSONObject("constraint").getJSONObject("name").getString("simpleName").replaceAll("\"","");
                        }else{
                            continue;
                        }
                    }
                }else{
                    //创建索引 解析json获取 索引名称、表空间
                    List<SQLObject> children = statement.getChildren();
                    for (SQLObject child : children) {
                        JSONObject childJsonObject = JSON.parseObject(JSON.toJSONString(child));
                        if(childJsonObject.containsKey("parent")){
                            if(null != childJsonObject.getJSONObject("parent").getJSONObject("tablespace")){
                                tablespaceName = childJsonObject.getJSONObject("parent").getJSONObject("tablespace").getString("simpleName").replaceAll("\"","");
                            }
                            indexName = childJsonObject.getJSONObject("parent").getJSONArray("children").getJSONObject(0).getString("simpleName").replaceAll("\"","");
                            String indexType = childJsonObject.getJSONObject("parent").getString("type");
                            if("UNIQUE".equals(indexType)) {
                                isUnique = SqlConstants.STRING_Y;
                            }
                        }else{
                            continue;
                        }
                    }
                }
                String columnName1 = columnName.toString().replaceAll("\"","");
                String columnName2 = columnName1.substring(0,columnName1.length()-1);
                index.setTableName(tableName.replace("\"",""));
                index.setIndexName(indexName);
                index.setIndexColumn(columnName2);
                index.setTableSpace(tablespaceName);
                index.setUniqueIndex(isUnique);
                index.setPrimaryKey(isPrimaryKey?SqlConstants.STRING_Y:SqlConstants.STRING_N);
                sqlIndexList.add(index);
            }else if(StringUtils.equals("Alter",operType)){
                //目前只支持增加字段 与 修改字段(修改字段不需要校验)
                SQLAlterTableStatement sqlAlterTableStatement;
                sqlAlterTableStatement =(SQLAlterTableStatement)statement;
                sqlAlterTableStatement.accept(oracleSchemaStatVisitor);
                if(sqlAlterTableStatement.toString().indexOf("\tADD ") > 0){
                    //sqlAlterTableStatement 添加字段的的输出是如上格式
                    List<SQLAlterTableItem> sqlAlterTableItemList=sqlAlterTableStatement.getItems();
                    SQLAlterTableAddColumn sqlAlterTableAddColumn=(SQLAlterTableAddColumn)sqlAlterTableItemList.get(0);

                    for(SQLColumnDefinition sqlColumnDefinition:sqlAlterTableAddColumn.getColumns()){
                        String nullAble = sqlColumnDefinition.toString().contains("NOT NULL")?SqlConstants.STRING_N:SqlConstants.STRING_Y;
                        String hasDefault = sqlColumnDefinition.toString().contains("DEFAULT")?SqlConstants.STRING_Y:SqlConstants.STRING_N;
                        List<SQLExpr> sqlExprList=sqlColumnDefinition.getDataType().getArguments();
                        String columnLength="";
                        if(sqlExprList != null && !sqlExprList.isEmpty()){
                            columnLength=sqlExprList.get(0).toString();
                        }
                        Column column=new Column();
                        column.setTableName(tableName.replace("\"",""));
                        column.setColumnName(sqlColumnDefinition.getNameAsString());
                        column.setColumnSize(columnLength);
                        column.setNullAble(nullAble);
                        column.setHasDefaultValue(hasDefault);
                        sqlColumnList.add(column);
                    }
                }
            }
        }
        for(Column column :sqlColumnList){
            column.setRemarks(colunmCmmentMap.getOrDefault(column.getTableName()+"."+column.getColumnName(),""));
        }
//        System.out.println("索引信息："+JSON.toJSONString(sqlIndexList));
//        System.out.println("字段信息："+JSON.toJSONString(sqlColumnList));
//        System.out.println("字段备注信息："+JSON.toJSONString(colunmCmmentMap));
//        System.out.println("Drop语句信息："+JSON.toJSONString(dropSqlList));
        }

    /**
     * DDL CREATE TABLE 语句解析
     * @param sql
     * @return
     */
    public static Table createTableSqlParse(String sql) {
        String dbType = JdbcConstants.ORACLE;
        SQLCreateTableStatement createTableStatement = null;
        Table sqlTable = new Table();
        // 设置默认没主键
        sqlTable.setHasPrimaryKey(SqlConstants.STRING_N);
        List<Column> sqlColumnList=new ArrayList<>();
        List<Index> sqlIndexList=new ArrayList<>();
        //字段注释map<字段名称，注释>
        Map<String,String> colunmCmmentMap = new HashMap<>(16);
        //字段判空 map<字段名称，Y/N>
        Map<String,String> colunmNullAbleMap = new HashMap<>(16);
        //字段默认值 map<字段名称，Y/N>
        Map<String,String> colunmHasDefaultValueMap = new HashMap<>(16);
        //开始解析
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        String tableName = null;
        TableStat operType = null;
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement statement = stmtList.get(i);
            OracleSchemaStatVisitor oracleSchemaStatVisitor = new OracleSchemaStatVisitor();
            statement.accept(oracleSchemaStatVisitor);
            if(tableName==null){
                tableName= oracleSchemaStatVisitor.getTables().keySet().iterator().next().toString();
            }
            operType = oracleSchemaStatVisitor.getTableStat(tableName.toString());
            //创建语句解析
            if(("Create").equals(operType.toString())){
                //先将statement变量保存，最后进行字段信息的解析
                createTableStatement = (SQLCreateTableStatement) statement;
                //通过字符串判断
                List<SQLObject> childrenList = statement.getChildren();
                for(SQLObject children :childrenList ){
                    String childrenRow = children.toString();
                    //第一条是表名，跳过
                    if(!childrenRow.equals(tableName)){
                        String tempColumnName = childrenRow.split("\\s")[0].replaceAll("\"","");
                        String nullAble = childrenRow.contains("NOT NULL")?SqlConstants.STRING_N:SqlConstants.STRING_Y;
                        String hasDefault = childrenRow.contains("DEFAULT")?SqlConstants.STRING_Y:SqlConstants.STRING_N;
                        //字段判空 map<字段名称，Y/N>
                        colunmNullAbleMap.put(tempColumnName,nullAble);
                        //字段默认值 map<字段名称，Y/N>
                        colunmHasDefaultValueMap.put(tempColumnName,hasDefault);

                    }
                }
            }else if(operType.toString().contains("Index")){
                Index index = new Index();
                String indexName ="";
                String tablespaceName = "";
                StringBuffer columnName = new StringBuffer();

                Collection<TableStat.Column> columns = oracleSchemaStatVisitor.getColumns();
                //不能使用parallelStream()异步遍历，此种遍历方式会导致顺序变化
                //columns.parallelStream().forEach(column->{columnName.append(JSON.parseObject(JSON.toJSONString(column), Feature.OrderedField).get("name")+",");});
                Iterator<TableStat.Column> columnIterator=columns.iterator();
                while(columnIterator.hasNext()){
                    columnName.append(JSON.parseObject(JSON.toJSONString(columnIterator.next()), Feature.OrderedField).get("name")+",");
                }
                JSONObject indexJson = JSON.parseObject(JSON.toJSONString(oracleSchemaStatVisitor.getColumns().iterator().next()));
                //System.out.println("indexJson:"+indexJson);
                boolean isPrimaryKey = indexJson.getBoolean("primaryKey");
                String isUnique = SqlConstants.STRING_N;
                if(isPrimaryKey){
                    // 设置表有主键
                    sqlTable.setHasPrimaryKey(SqlConstants.STRING_Y);
                    //创建主键 解析json获取 主键名称
                    List<SQLObject> children = statement.getChildren();
                    for (SQLObject child : children) {
                        JSONObject childJsonObject = JSON.parseObject(JSON.toJSONString(child));
                        if(childJsonObject.containsKey("constraint")){
                            indexName = childJsonObject.getJSONObject("constraint").getJSONObject("name").getString("simpleName").replaceAll("\"","");
                        }else{
                            continue;
                        }
                    }
                }else{
                    //创建索引 解析json获取 索引名称、表空间
                    List<SQLObject> children = statement.getChildren();
                    for (SQLObject child : children) {
                        JSONObject childJsonObject = JSON.parseObject(JSON.toJSONString(child));
                        if(childJsonObject.containsKey("parent")){
                            if(null != childJsonObject.getJSONObject("parent").getJSONObject("tablespace")){
                                tablespaceName = childJsonObject.getJSONObject("parent").getJSONObject("tablespace").getString("simpleName").replaceAll("\"","");
                            }
                            indexName = childJsonObject.getJSONObject("parent").getJSONArray("children").getJSONObject(0).getString("simpleName").replaceAll("\"","");
                            String indexType = childJsonObject.getJSONObject("parent").getString("type");
                            if("UNIQUE".equals(indexType)) {
                                isUnique = SqlConstants.STRING_Y;
                            }
                        }else{
                            continue;
                        }
                    }
                }
                String columnName1 = columnName.toString().replaceAll("\"","");
                String columnName2 = columnName1.substring(0,columnName1.length()-1);

                index.setIndexName(indexName);
                index.setIndexColumn(columnName2);
                index.setTableSpace(tablespaceName);
                index.setUniqueIndex(isUnique);
                index.setPrimaryKey(isPrimaryKey?SqlConstants.STRING_Y:SqlConstants.STRING_N);
                sqlIndexList.add(index);
            }else if(statement.toString().indexOf("COMMENT")==0){
                SQLCommentStatement stmt = (SQLCommentStatement)statement;
                SQLCharExpr sqlCharExpr=(SQLCharExpr)stmt.getComment();
                String columnName = stmt.getOn().getName().getSimpleName().replaceAll("\"","");
                String commentText = sqlCharExpr.getText();

                if("TABLE".equals(stmt.getType().toString())){
                    sqlTable.setComment(commentText);
                }else{
                    colunmCmmentMap.put(columnName,commentText);
                }
            }
        }
        //解析建表的字段
        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
        if(null!=createTableStatement){
            createTableStatement.accept(visitor);
            //取出表名和schema
            if(StringUtils.isNotBlank(createTableStatement.getSchema())){
                String schema = createTableStatement.getSchema().replaceAll("\"","");
                sqlTable.setSchema(schema);
            }
            tableName = createTableStatement.getName().getSimpleName().replaceAll("\"","");
            sqlTable.setTableName(tableName);
            Collection<TableStat.Column> columns = visitor.getColumns();
            for(TableStat.Column column:columns){
                String name = column.getName().replaceAll("\"","");
                String dataType = column.getDataType();
                boolean isPrimaryKey = column.isPrimaryKey();
                SQLColumnDefinition columnDefinition = createTableStatement.findColumn(name);
                List<SQLExpr> sqlExprList=columnDefinition.getDataType().getArguments();
                String columnLength="";
                if(sqlExprList != null && !sqlExprList.isEmpty()){
                    columnLength=sqlExprList.get(0).toString();
                }
                // 字段注释
                String commentText = colunmCmmentMap.get(name);
                //字段默认值
                String hasDefaultValue = colunmHasDefaultValueMap.get(name);
                //是否为空
                String nullAble = colunmNullAbleMap.get(name);

                Column sqlColumn = new Column();
                sqlColumn.setColumnName(name);
                sqlColumn.setTypeName(StringUtils.upperCase(dataType));
                sqlColumn.setColumnSize(columnLength);
                sqlColumn.setRemarks(commentText);
                sqlColumn.setNullAble(nullAble);
                sqlColumn.setHasDefaultValue(hasDefaultValue);
                sqlColumnList.add(sqlColumn);
                if(isPrimaryKey){
                    sqlTable.setHasPrimaryKey(SqlConstants.STRING_Y);
                }
            }
            sqlTable.setColumnList(sqlColumnList);
            sqlTable.setIndexList(sqlIndexList);
        }
        return sqlTable;
    }


}
