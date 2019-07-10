package com.wxy.wjl.testng;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestNGRetryListener implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
		//获取到retryAnalyzer的注解
		IRetryAnalyzer retryAnalyzer = iTestAnnotation.getRetryAnalyzer();
		if (retryAnalyzer == null) {
			System.out.println("动态设置注解");
			//如果注解为空，则动态设置注解，以确保用例失败后重跑。
			iTestAnnotation.setRetryAnalyzer(TestNGRetry.class);
		}
	}
}

