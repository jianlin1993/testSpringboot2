package com.wxy.wjl.testspringboot2.sqlcheck.model;



public enum CommonMessageCode {
	/**
	 * 成功
	 */
	SUCC("SCM00000", "Success"),
	FAIL("SCM99999", "Fail"),

	//Sql审核相关
    SQL_CHECK_FAILED("SCM10000","Sql check failed ! "),
	ILLEGAL_SQL_TYPE("SCM10001","Illegal sql type!"),
	NO_VALID_RULES("SCM10002","No valid verification rules!"),
	INSERT_SQL_CHECK_RECORD_FAILED("SCM10003","Failed to insert sql check record!"),
    SQL_SYNTAX_ERROR("SCM10004","sql syntax error ! Please check if the sql is correct"),
    ILLEGAL_DML_RULE("SCM10005","Illegal DML rule"),

	;
	private final String msgCod;
	private final String msgInf;

	CommonMessageCode(String msgCod, String msgInf) {
		this.msgCod = msgCod;
		this.msgInf = msgInf;
	}

	public String getMsgCod() {
		return msgCod;
	}

	public String getMsgInf() {
		return msgInf;
	}
}
