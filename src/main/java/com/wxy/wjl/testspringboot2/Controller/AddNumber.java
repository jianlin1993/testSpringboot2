package com.wxy.wjl.testspringboot2.Controller;

public class AddNumber {

	public boolean addNum() throws Exception{
		AddCO addCO=new AddCO();
		String a=add(addCO);
		sub();
		if(addCO.isSuccess()){
			return true;
		}
		System.out.println("add返回："+a);
		return false;
	}
	public String add(AddCO co) throws Exception{
		//throw new RuntimeException();
		System.out.println("调用到了");
		return "1";
	}
	private void sub(){
		System.out.println("sub调用了");
	}
}
