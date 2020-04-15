package com.wxy.wjl.testng;

import com.wxy.wjl.testng.common.Constant;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.collections.Lists;
import java.util.List;

public class TestNGListener extends TestListenerAdapter  {

	private List<String> testFailed= Lists.newArrayList();
	private List<String> testPassed= Lists.newArrayList();
	//执行测试方法计数器
	private Integer retryCount=1;

	@Override
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		//执行成功直接重置计数器
		retryCount=1;
	}


//	@Override
//	public void onTestFailure(ITestResult result) {
//		super.onTestFailure(result);
//		testFailed.add(result.getMethod().getMethodName());
//		retryCount++;
//		//是否达到重发最大次数
//		if(retryCount % Constant.MAX_RETRY_NUM ==0){
//			TestNGRetry retryAnalyzer = (TestNGRetry) result.getMethod().getRetryAnalyzer();
//			System.out.println("重置失败次数");
//			retryAnalyzer.reSetCount();
//		}
//	}
}
