package com.wxy.wjl.testng.test;



import com.wxy.wjl.testspringboot2.Controller.AddCO;
import com.wxy.wjl.testspringboot2.Controller.AddNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * jacoco离线模式版代码
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AddNumber.class,AddNumber.class})
@PowerMockIgnore("javax.management.*")
public class AddNumber2Test {

	@InjectMocks
	AddNumber addNumber;

	/**
	 * 使用此方式进行mock，整个流程（addNum方法）实际执行，也会调用实际方法（add），依赖运行环境
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception{
		AddNumber spy= Mockito.spy(addNumber);
		Mockito.when(spy.add(Mockito.any())).thenReturn("2");
		//
		System.out.println(spy.addNum());
	}
	/**
	 * 推荐使用此方式
	 * 使用此方式进行mock，整个流程（addNum方法）实际执行，不会调用实际方法（add）
	 * @throws Exception
	 */
	@Test
	public void testDo() throws Exception{
		AddNumber spy= Mockito.spy(addNumber);
		Mockito.doReturn("twotwo").when(spy).add(Mockito.any());
		System.out.println(spy.addNum());
	}

	/**
	 * 使用PowerMock   与mock私有方法方式相同  整个流程实际调用  add模拟调用
	 * @throws Exception
	 */
	@Test
	public void test03() throws Exception{
		AddNumber spy= PowerMockito.spy(addNumber);
		System.out.println(spy);
		PowerMockito.doReturn("2").when(spy,"add", Mockito.any());
		System.out.println(spy.addNum());
	}

	/**
	 * 使用此方式进行mock，是对整个流程（addNum方法）mock，不会调用实际方法
	 * @throws Exception
	 */
	@Test
	public void test04() throws Exception{
		AddNumber mock= Mockito.mock(AddNumber.class);
		System.out.println(mock);
		Mockito.when(mock.add(Mockito.any())).thenReturn("2");
		System.out.println("addNum返回："+mock.addNum());
	}


	/**
	 * doCallRealMethod() 使用此方式进行mock，整个流程（addNum方法）被mock，根据插桩参数执行实际方法
	 * @throws Exception
	 */
	@Test
	public void test06() throws Exception{
		AddNumber mock= Mockito.mock(AddNumber.class);
		AddCO addCO1=new AddCO();
		AddCO addCO2=new AddCO();
		Mockito.doCallRealMethod().when(mock).add(addCO1);
		System.out.println(mock.add(addCO1));
		System.out.println(mock.add(addCO2));
		System.out.println(mock.addNum());
	}

	/*	*//**
	 * PowerMockito spy mock无返回值的公共方法
	 * @throws Exception
	 *//*
	@Test
	public void test07() throws Exception{
		AddNumber spy=PowerMockito.spy(addNumber);
		PowerMockito.doNothing().when(spy).sub();
		System.out.println(spy.addNum());
	}
	*//**
	 * Mockito spy mock无返回值的公共方法
	 * @throws Exception
	 *//*
	@Test
	public void test09() throws Exception{
		AddNumber spy=Mockito.spy(addNumber);
		Mockito.doNothing().when(spy).sub();
		System.out.println(spy.addNum());
	}


	*//**
	 * Mockito doAnswer mock无返回值的公共方法
	 * @throws Exception
	 *//*
	@Test
	public void test10() throws Exception{
		AddNumber spy = Mockito.spy(addNumber);
		Mockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return "called with arguments: " + args;
			}
		}).when(spy).sub();
		System.out.println(spy.addNum());
	}*/


	/**
	 * PowerMockito spy mock无返回值的私有方法
	 * @throws Exception
	 */
	@Test
	public void test09() throws Exception{
		AddNumber spy= PowerMockito.spy(addNumber);
		PowerMockito.doNothing().when(spy,"sub");//若sub方法有参数，在括号中加逗号和参数即可
		System.out.println(spy.addNum());
	}


	/**
	 * doCallRealMethod() mock无返回值的方法
	 * @throws Exception
	 */
	@Test
	public void test08() throws Exception{
		AddNumber mock= PowerMockito.mock(AddNumber.class);
		Mockito.doCallRealMethod().when(mock).add(Mockito.any());
		//Mockito.doCallRealMethod().when(mock).sub();
		System.out.println(mock.addNum());
	}

	@Test
	public void test01() throws Exception{
		AddCO addCO= PowerMockito.mock(AddCO.class);
		PowerMockito.whenNew(AddCO.class).withAnyArguments().thenReturn(addCO);
		PowerMockito.when(addCO.isSuccess()).thenReturn(true);
		System.out.println(addNumber.addNum());
	}



}
