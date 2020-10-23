package com.wxy.wjl.testspringboot2.sqlcheck;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.wxy.wjl.testspringboot2.sqlcheck.constants.SqlConstants;
import com.wxy.wjl.testspringboot2.sqlcheck.enums.SqlRuleTypeEnum;
import com.wxy.wjl.testspringboot2.sqlcheck.enums.SqlTypeEnum;
import com.wxy.wjl.testspringboot2.sqlcheck.model.*;
import com.wxy.wjl.testspringboot2.sqlcheck.utils.SqlCheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SqlCheckService {

    public static final String databaseType= JdbcConstants.ORACLE;

    public ResultT checkSql(ResultT result, SqlCheckReqBO reqBO, List<BuiSqlRuleDO> buiSqlRuleDOList, Logger logger){
        logger.info("The sql type is "+ SqlTypeEnum.getDescByvalue(reqBO.getSqlType()));
        //格式化sql
        String formatSql="";
        try{
            formatSql= SQLUtils.format(reqBO.getSqlContent(), databaseType);
        }catch (ParserException e){
            logger.info("sql syntax error ! "+e.getMessage());
            result.setMsgCod(CommonMessageCode.SQL_SYNTAX_ERROR);
            return result;
        }
        logger.info("SQL after format ="+formatSql);
        //根据sql类型 校验sql
        switch(SqlTypeEnum.getSqlTypeEnumByValue(reqBO.getSqlType())){
            case DML:
                result=checkDmlSql(result,formatSql,buiSqlRuleDOList,logger);
                break;
            case DDL_CREATE:
                if(formatSql.trim().indexOf("CREATE TABLE") != 0){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf("DDL(CREATE TABLE)语句-请把CREATE TABLE语句放在最前面!");
                    return result;
                }
                if(formatSql.indexOf("CREATE TABLE") != formatSql.lastIndexOf("CREATE TABLE")){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf("DDL(CREATE TABLE)语句-只支持单个表的创建!");
                    return result;
                }
                Table tableCheck = SqlCheckUtil.createTableSqlParse(formatSql);
                result=checkCreateTableDDLRule(result,tableCheck,buiSqlRuleDOList,logger);
                break;
            case DDL_ALTER:
                List<Column> sqlColumnList=new ArrayList<>();
                List<Index> sqlIndexList=new ArrayList<>();
                List<String> dropSqlList=new ArrayList<>();
                SqlCheckUtil.alterTableSqlParse(formatSql,sqlColumnList,sqlIndexList,dropSqlList);
                result=checkDdlAlterSql(result,sqlColumnList,sqlIndexList,buiSqlRuleDOList,dropSqlList,logger);
                break;
            case SEQUENCE:
                result=checkSequenceSql(result,formatSql,buiSqlRuleDOList,logger);
                break;
            default:
        }

        return result;
    }

    public ResultT checkDmlSql(ResultT result, String formatSql, List<BuiSqlRuleDO> buiSqlRuleDOList, Logger logger){
        //根据;分割成单个sql  根据规则逐一检测
        String [] sqlList=formatSql.trim().split(";");
        for(int i=0;i<sqlList.length;i++){
            String sql = sqlList[i];
          //  logger.infoFmt("Processing the {} SQL; SQL = [ {} ]", (i + 1), sql);
            if (StringUtils.isBlank(sql)) {
                continue;
            }
            //解析单条sql 直接取第一条
            List<SQLStatement> sqlStatementList= SQLUtils.parseStatements(sql, databaseType);
            SQLStatement sqlStatement=sqlStatementList.get(0);
            OracleSchemaStatVisitor visitor=new OracleSchemaStatVisitor();
            sqlStatement.accept(visitor);
            //遍历每一条规则
            for(BuiSqlRuleDO buiSqlRuleDO:buiSqlRuleDOList){
                switch (SqlRuleTypeEnum.getSqlRuleTypeEnumByRuleId(buiSqlRuleDO.getRuleId())){
                    case RULE_1:
                        //UPDATE语句禁止出现ORDER BY子句
                        result=checkRule1(result,sql,visitor,logger);
                        break;
                    case RULE_2:
                        //UPDATE语句必须出现WHERE子句
                        result=checkRule2(result,sql,visitor,logger);
                        break;
                    case RULE_3:
                        //DELETE语句必须出现WHERE子句
                        result=checkRule3(result,sql,visitor,logger);
                        break;
                    case RULE_4:
                        //DELETE语句禁止出现ORDER BY子句
                        result=checkRule4(result,sql,visitor,logger);
                        break;
                    case RULE_5:
                        //SELECT语句必须出现WHERE子句
                        result=checkRule5(result,sql,visitor,logger);
                        break;
                    case RULE_6:
                        //禁止DROP操作
                        result=checkRule6(result,sql,visitor,logger);
                        break;
                    case RULE_7:
                        //禁止TRUNCATE操作
                        result=checkRule7(result,sql,visitor,logger);
                        break;
                    default:
                      //  logger.infoFmt("Illegal DML rule = {}",buiSqlRuleDO.getRuleDesc());
                }
                if(result.isFail()){
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + "SQL = "+sql+", ruleDesc = "+buiSqlRuleDO.getRuleDesc());
                    return result;
                }
            }
        }
        return result;
    }

    public ResultT checkDdlAlterSql(ResultT result, List<Column> columnList,List<Index> indexList, List<BuiSqlRuleDO> buiSqlRuleDOList,List<String> dropSqlList, Logger logger){
        //遍历规则
        for(BuiSqlRuleDO buiSqlRuleDO:buiSqlRuleDOList){
            switch (SqlRuleTypeEnum.getSqlRuleTypeEnumByRuleId(buiSqlRuleDO.getRuleId())){
                case RULE_6:
                    if(!dropSqlList.isEmpty()){
                        result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                        result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_6.getRuleDesc());
                        return result;
                    }
                    break;
                case RULE_9:
                    //字段必须有注释
                    result=checkRule9(result,columnList,logger);
                    break;
                case RULE_10:
                    //非CLOB字段必须NOT NULL
                    result=checkRule10(result,columnList,logger);
                    break;
                case RULE_11:
                    //非CLOB字段必须有默认值
                    result=checkRule11(result,columnList,logger);
                    break;
                case RULE_13:
                    //字段定义必须符合字典定义
                    result=checkRule13(result,columnList,logger);
                    break;
                case RULE_17:
                    //字段名命名不符合规范 todo

                    break;
                case RULE_18:
                    //索引命名不符合规范(UI1_TABLENAME、NI1_TABLENAME)
                    result=checkAlterTableRule18(result,indexList,logger);
                    break;
                case RULE_19:
                    //索引字段超过5个
                    result=checkRule19(result,indexList,logger);
                    break;
                case RULE_20:
                    //索引个数超过4个
                    result=checkAlterTableRule20(result,indexList,logger);
                    break;
                case RULE_21:
                    //冗余索引(指该索引可以使用其他索引替代)
                    result=checkAlterTableRule21(result,indexList,logger);
                    break;
                case RULE_22:
                    //索引必须指定TABLESPACE
                    result=checkRule22(result,indexList,logger);
                    break;
                default:
                   // logger.infoFmt("Illegal DDL(Alter TABLE) rule = {}",buiSqlRuleDO.getRuleDesc());
                    break;
            }
            if(result.isFail()){
                return result;
            }
        }


        return result;
    }
    public ResultT checkSequenceSql(ResultT result, String formatSql, List<BuiSqlRuleDO> buiSqlRuleDOList, Logger logger){
        String [] sqlList=formatSql.split(";");
        for(int i=0;i<sqlList.length;i++) {
            String sql = sqlList[i];
            //logger.infoFmt("Processing the {} SQL; sequence SQL = [ {} ]", (i + 1), sql);
            if (StringUtils.isBlank(sql)) {
                continue;
            }
            OracleSchemaStatVisitor visitor=new OracleSchemaStatVisitor();
            for(BuiSqlRuleDO buiSqlRuleDO:buiSqlRuleDOList){
                switch (SqlRuleTypeEnum.getSqlRuleTypeEnumByRuleId(buiSqlRuleDO.getRuleId())) {
                    case RULE_23:
                        result=checkSequenceRule1(result,sql,visitor,logger);
                        break;
                    default:
                      //  logger.infoFmt("Illegal Sequence rule = {}",buiSqlRuleDO.getRuleDesc());
                }
                if(result.isFail()){
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf()+",ruleDesc = "+buiSqlRuleDO.getRuleDesc());
                    return result;
                }
            }
        }

        return result;
    }

    public ResultT checkRule1(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        //此规则在sql format时即校验了sql语法
        return result;
    }

    /**
     * 如果是UPDATE语句 判断是否有where条件
     * @param result
     * @param sql
     * @param visitor
     * @param logger
     * @return
     */
    public ResultT checkRule2(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        if(sql.trim().indexOf("UPDATE") == 0){
            if(!SqlCheckUtil.hasWhereContion(visitor)){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
            }
        }
        return result;
    }

    /**
     * 如果是DELETE语句 判断是否有where条件
     * @param result
     * @param sql
     * @param visitor
     * @param logger
     * @return
     */
    public ResultT checkRule3(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        if(sql.trim().indexOf("DELETE") == 0){
            if(!SqlCheckUtil.hasWhereContion(visitor)){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
            }
        }
        return result;
    }
    public ResultT checkRule4(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        //此规则在sql format时即校验了sql语法
        return result;
    }

    /**
     * 如果是SELECT语句 判断是否有where条件
     * @param result
     * @param sql
     * @param visitor
     * @param logger
     * @return
     */
    public ResultT checkRule5(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        if(sql.trim().indexOf("SELECT") == 0){
            if(!SqlCheckUtil.hasWhereContion(visitor)){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
            }
        }
        return result;
    }

    /**
     * 禁止DROP语句
     * @param result
     * @param sql
     * @param visitor
     * @param logger
     * @return
     */
    public ResultT checkRule6(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        if(sql.trim().indexOf("DROP") == 0){
            result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
        }
        return result;
    }

    /**
     * 禁止TRUNCATE操作
     * @param result
     * @param sql
     * @param visitor
     * @param logger
     * @return
     */
    public ResultT checkRule7(ResultT result, String sql, SchemaStatVisitor visitor, Logger logger){
        if(sql.trim().indexOf("TRUNCATE") == 0){
            result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
        }
        return result;
    }

    public ResultT checkSequenceRule1(ResultT resultT, String sql, SchemaStatVisitor visitor, Logger logger) {
        String regex = "\\s{0,12}CREATE\\s+SEQUENCE\\s+\"{0,1}.{0,30}\\.{0,1}\"{0,1}S_.+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        boolean flag = matcher.find();
        if (!flag) {
            resultT.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED);
        }
        return resultT;
    }

    public ResultT parseCreateTableSql(ResultT resultT,String sql,Table table,Logger logger){


        return resultT;
    }

    /**
     * 检查CREATE TABLE DDL所有规则
     * @param result
     * @param table
     * @param buiSqlRuleDOList
     * @param logger
     * @return
     */
    public ResultT checkCreateTableDDLRule(ResultT result,Table table,List<BuiSqlRuleDO> buiSqlRuleDOList,Logger logger){
        List<Index> indexList =table.getIndexList();
        List<Column> columnList = table.getColumnList();
        if(StringUtils.isBlank(table.getSchema())){
            result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
            result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + "Create Table 必须指定Schema ！");
            return result;
        }
        //遍历规则
        for(BuiSqlRuleDO buiSqlRuleDO:buiSqlRuleDOList){
            switch (SqlRuleTypeEnum.getSqlRuleTypeEnumByRuleId(buiSqlRuleDO.getRuleId())){
                case RULE_8:
                    //表必须有注释
                    if(StringUtils.isBlank(table.getComment())){
                        result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                        result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + buiSqlRuleDO.getRuleDesc());
                    }
                    break;
                case RULE_9:
                    //字段必须有注释
                    result=checkRule9(result,columnList,logger);
                    break;
                case RULE_10:
                    //非CLOB字段必须NOT NULL
                    result=checkRule10(result,columnList,logger);
                    break;
                case RULE_11:
                    //非CLOB字段必须有默认值
                    result=checkRule11(result,columnList,logger);
                    break;
                case RULE_12:
                    //表必须有主键
                    if(!StringUtils.equals(SqlConstants.STRING_Y,table.getHasPrimaryKey())){
                        result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                        result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + buiSqlRuleDO.getRuleDesc());
                        return result;
                    }
                    result=checkRule12(result,table.getTableName(),indexList,logger);
                    break;
                case RULE_13:
                    //字段定义必须符合字典定义
                    result=checkRule13(result,columnList,logger);
                    break;
                case RULE_14:
                    //表必须有TM_SMP、NOD_ID、REQ_ID公共字段
                    result=checkRule14(result,columnList,logger);
                    break;
                case RULE_15:
                    //表必须有CRE_OPR、UPD_OPR、CRE_TS、UPD_TS公共字段
                    result=checkRule15(result,columnList,logger);
                    break;
                case RULE_16:
                    //表名命名不符合规范
                    String tableNamePattern = "^T_[A-Z]{3}_\\w+";
                    if(!table.getTableName().matches(tableNamePattern)){
                        result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                        result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + buiSqlRuleDO.getRuleDesc() +" 表名："+table.getTableName());
                    }
                    break;
                case RULE_17:
                    //字段名命名不符合规范 todo

                    break;
                case RULE_18:
                    //索引命名不符合规范(UI1_TABLENAME、NI1_TABLENAME)
                    result=checkRule18(result,table.getTableName(),indexList,logger);
                    break;
                case RULE_19:
                    //索引字段超过5个
                    result=checkRule19(result,indexList,logger);
                    break;
                case RULE_20:
                    //索引个数超过4个
                    result=checkCreateTableRule20(result,indexList,logger);
                    break;
                case RULE_21:
                    //冗余索引(指该索引可以使用其他索引替代)
                    result=checkCreateTableRule21(result,indexList,logger);
                    break;
                case RULE_22:
                    //索引必须指定TABLESPACE
                    result=checkRule22(result,indexList,logger);
                    break;
                default:
                    //logger.infoFmt("Illegal DDL(CREATE TABLE) rule = {}",buiSqlRuleDO.getRuleDesc());
            }
            if(result.isFail()){
                return result;
            }
        }

        return result;
    }


    /**
     * 字段必须有注释
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule9(ResultT result, List<Column> columnList, Logger logger){
        for(Column column:columnList){
            if(StringUtils.isBlank(column.getRemarks()) || column.getRemarks().indexOf("|") <= 0){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_9.getRuleDesc() +" 字段名:"+column.getColumnName());
                return result;
            }
        }
        return result;
    }

    /**
     * 非CLOB字段必须NOT NULL
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule10(ResultT result, List<Column> columnList, Logger logger){
        for(Column column:columnList){
            if(!StringUtils.equals("CLOB",column.getTypeName())){
                if(!StringUtils.equals(SqlConstants.STRING_N,column.getNullAble())){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_10.getRuleDesc() +" 字段名:"+column.getColumnName());
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 非CLOB字段必须有默认值
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule11(ResultT result, List<Column> columnList, Logger logger){
        for(Column column:columnList){
            if(!StringUtils.equals("CLOB",column.getTypeName())){
                if(!StringUtils.equals(SqlConstants.STRING_Y,column.getHasDefaultValue())){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_11.getRuleDesc() +" 字段名:"+column.getColumnName());
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 表必须有主键(PK_TABLENAME)
     * @param result
     * @param tableName
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkRule12(ResultT result, String tableName,List<Index> indexList, Logger logger){
        String pkName = "PK_"+tableName;
        for(Index index:indexList){
            if(StringUtils.equals(SqlConstants.STRING_Y,index.getPrimaryKey())){
                if(!StringUtils.equals(pkName,index.getIndexName())){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_12.getRuleDesc() + " 主键名: "+index.getIndexName());
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 字段定义必须符合字典定义
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule13(ResultT result, List<Column> columnList, Logger logger){
/*        for(Column column:columnList){
            String dctNm=column.getRemarks().split("\\|")[0].trim();
            ParDatDctCO parDatDctCO=null;
            try{
                parDatDctCO=PltParUtil.getDatDctFromCache(dctNm,logger);
            }catch (YGException e){
                continue;
            }
            if(parDatDctCO != null){
                if(!StringUtils.equals(column.getTypeName(),parDatDctCO.getDctTyp())
                        || !StringUtils.equals(column.getColumnSize(),parDatDctCO.getDctLen()) ){
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_13.getRuleDesc()
                            + " ,字段名:"+column.getColumnName()+" ,字典名:"+dctNm+" ,字典中数据类型:"+parDatDctCO.getDctTyp() + " ,长度 :"+parDatDctCO.getDctLen());
                    return result;
                }
            }
        }*/
        return result;
    }

    /**
     * 表必须有TM_SMP、NOD_ID、REQ_ID公共字段
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule14(ResultT result, List<Column> columnList, Logger logger){
        Set<String> columnSet = columnList.stream().map(column->column.getColumnName()).collect(Collectors.toSet());
        for(int i=0;i<SqlConstants.BASE_COLUMN1.length;i++){
            if(!columnSet.contains(SqlConstants.BASE_COLUMN1[i])){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_14.getRuleDesc());
                return result;
            }
        }
        return result;
    }

    /**
     * 表必须有CRE_OPR、UPD_OPR、CRE_TS、UPD_TS公共字段
     * @param result
     * @param columnList
     * @param logger
     * @return
     */
    public ResultT checkRule15(ResultT result, List<Column> columnList, Logger logger){
        Set<String> columnSet = columnList.stream().map(column->column.getColumnName()).collect(Collectors.toSet());
        for(int i=0;i<SqlConstants.BASE_COLUMN2.length;i++){
            if(!columnSet.contains(SqlConstants.BASE_COLUMN2[i])){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_15.getRuleDesc());
                return result;
            }
        }
        return result;
    }

    /**
     * 索引命名不符合规范(UI1_TABLENAME、NI1_TABLENAME)
     * @param result
     * @param tableName
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkRule18(ResultT result, String tableName,List<Index> indexList, Logger logger){
        HashSet<String> indexSet=new HashSet<>(16);
        String uniqueIndexPattern = "^UI\\d+_"+tableName;
        String normalIndexPattern = "^NI\\d+_"+tableName;
        boolean matchResult=false;
        for(Index index:indexList){
            if(indexSet.contains(index.getIndexName())){
                //判断索引名是否重复
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_18.getRuleDesc() + " 发现重复索引名: "+index.getIndexName());
                return result;
            }else{
                indexSet.add(index.getIndexName());
            }
            if(StringUtils.equals(SqlConstants.STRING_Y,index.getPrimaryKey())){
                continue;
            }
            if(StringUtils.equals(SqlConstants.STRING_Y,index.getUniqueIndex())){
                matchResult=index.getIndexName().matches(uniqueIndexPattern);
            }else{
                matchResult=index.getIndexName().matches(normalIndexPattern);
            }
            if(!matchResult){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_18.getRuleDesc() + " 索引名: "+index.getIndexName());
                return result;
            }
        }
        return result;
    }

    /**
     * 索引字段超过5个
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkRule19(ResultT result, List<Index> indexList, Logger logger){
        for(Index index:indexList){
            String indexColumnsArray []= StringUtils.split(index.getIndexColumn(),",");
            if(indexColumnsArray.length >= 5){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_19.getRuleDesc() + " 索引名: "+index.getIndexName());
                return result;
            }
        }
        return result;
    }

    /**
     * 索引个数超过4个 因为indexList中包含了主键  所以判断用大于5
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkCreateTableRule20(ResultT result, List<Index> indexList, Logger logger){
        int indexNums=indexList.size()-1;
        if( indexNums > 4){
            result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
            result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() +
                    SqlRuleTypeEnum.RULE_20.getRuleDesc() + ", 创建的索引个数为: "+ indexNums);
        }
        return result;
    }

    /**
     * 冗余索引(指该索引可以使用其他索引替代)
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkCreateTableRule21(ResultT result, List<Index> indexList, Logger logger){
        //遍历list 判断各个索引字段是否相互包含
        for(int i=0;i<indexList.size();i++){
            for(int j=indexList.size()-1;j>=0;j--){
                if(i == j){
                    continue;
                }
                Index indexI=indexList.get(i);
                Index indexJ=indexList.get(j);
                if(indexI.getIndexColumn().indexOf(indexJ.getIndexColumn()) == 0){
                    //判断某个索引字段以另一个索引字段开头并且顺序相同 依据索引前缀匹配规则  判定此两条索引冗余
                    result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                    result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_21.getRuleDesc() +
                            ", 发现冗余索引，请判断是否有必要都创建: "+indexI.getIndexName()+" 和 "+indexJ.getIndexName());
                    return result;
                }
            }
        }

        return result;
    }

    /**
     * 索引必须指定TABLESPACE
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkRule22(ResultT result, List<Index> indexList, Logger logger){
        for(Index index:indexList){
            if(StringUtils.equals(SqlConstants.STRING_Y,index.getPrimaryKey())){
                continue;
            }
            if(StringUtils.isBlank(index.getTableSpace())){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_22.getRuleDesc() + "索引名为: "+index.getIndexName());
                return result;
            }
        }
        return result;
    }


    /**
     * DDL 修改 索引命名不规范
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkAlterTableRule18(ResultT result, List<Index> indexList, Logger logger){
        boolean matchResult=false;
        for(Index index:indexList){
            if(index.getTableName().indexOf(".") <= 0){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + " 索引名: "+index.getIndexName() + " ,必须指定Schema");
                return result;
            }
            String tableName=index.getTableName().split("\\.")[1];
            String uniqueIndexPattern = "^UI\\d+_"+tableName;
            String normalIndexPattern = "^NI\\d+_"+tableName;
            String pkPattern = "PK_"+tableName;
            if(StringUtils.equals(SqlConstants.STRING_Y,index.getPrimaryKey())){
                matchResult= StringUtils.equals(pkPattern,index.getIndexName());
            }else if(StringUtils.equals(SqlConstants.STRING_Y,index.getUniqueIndex())){
                matchResult=index.getIndexName().matches(uniqueIndexPattern);
            }else{
                matchResult=index.getIndexName().matches(normalIndexPattern);
            }
            if(!matchResult){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_18.getRuleDesc() + " 索引名: "+index.getIndexName());
                return result;
            }
        }
        return result;
    }

    /**
     * 索引个数超过4个
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkAlterTableRule20(ResultT result, List<Index> indexList, Logger logger){
        //先遍历索引  确定索引list中每个表的索引个数
/*        Map<String,Integer> indexSumMap=new HashMap<>();
        for(Index index:indexList){
            if(indexSumMap.containsKey(index.getTableName())){
                indexSumMap.put(index.getTableName(),indexSumMap.get(index.getTableName())+1);
            }else{
                indexSumMap.put(index.getTableName(),1);
            }
        }
        for(Index index:indexList){
            int alreadyExistsIndexNums=PltParUtil.getIndexNumFromCache(index.getTableName(),logger);
            int sums=alreadyExistsIndexNums+indexSumMap.get(index.getTableName());
            if( sums> 4){
                result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() +
                        SqlRuleTypeEnum.RULE_20.getRuleDesc() + " ,表名："+index.getTableName()
                        +", 表中已有索引个数为: "+alreadyExistsIndexNums +" ,本次创建索引个数为："+indexSumMap.get(index.getTableName()));
            }

        }*/
        return result;
    }


    /**
     * 冗余索引
     * @param result
     * @param indexList
     * @param logger
     * @return
     */
    public ResultT checkAlterTableRule21(ResultT result, List<Index> indexList, Logger logger){
/*        String alreadyExistsIndexList[] = null;
        String alreadyExistsIndexColumns="";
        String curIndexColumns="";
        for(Index index:indexList){
            alreadyExistsIndexList = PltParUtil.getIndexListFromCache(index.getTableName(),logger);
            if(alreadyExistsIndexList == null){
                continue;
            }else{
                curIndexColumns=index.getIndexColumn();
                for(int i=0;i<alreadyExistsIndexList.length;i++){
                    //获取每个索引的字段
                    alreadyExistsIndexColumns = PltParUtil.getIndexColumnsFromCache(index.getTableName()+"."+alreadyExistsIndexList[i],logger);
                    if(StringUtils.isBlank(alreadyExistsIndexColumns)){
                        continue;
                    }
                    if(curIndexColumns.indexOf(alreadyExistsIndexColumns) == 0 || alreadyExistsIndexColumns.indexOf(curIndexColumns) == 0){
                        result.setMsgCod(CommonMessageCode.SQL_CHECK_FAILED.getMsgCod());
                        result.setMsgInf(CommonMessageCode.SQL_CHECK_FAILED.getMsgInf() + SqlRuleTypeEnum.RULE_21.getRuleDesc() +
                                ", 发现冗余索引，请确认是否有必要创建，本次创建索引为："+index.getIndexName()+"，字段为："+index.getIndexColumn()
                                + ", 表中已有索引相似索引为："+alreadyExistsIndexList[i] +" , 字段为："+alreadyExistsIndexColumns);
                        return result;
                    }
                }
            }
        }*/
        return result;
    }


}
