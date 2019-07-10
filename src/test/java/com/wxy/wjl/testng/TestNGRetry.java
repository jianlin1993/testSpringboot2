package com.wxy.wjl.testng;

import com.wxy.wjl.testng.common.Constant;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 测试案例失败重发
 */
public class TestNGRetry  implements IRetryAnalyzer  {

	private Integer retryCount=1;
	private Integer maxRetryCount= Constant.MAX_RETRY_NUM;


	public boolean retry(ITestResult result){
		System.out.println("执行用例："+result.getName()+"，第"+retryCount+"次失败");
		if(retryCount < maxRetryCount){
			retryCount++;
			return true;
		}
		return false;
	}

	public void reSetCount(){
		retryCount=1;
	}
}
