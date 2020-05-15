package com.wxy.wjl.testspringboot2.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理类
 */
public class CglibProxy implements MethodInterceptor {
    public CglibProxy(){

    }
    private Object target;
    public Object getInstance(final Object target) {
        this.target = target;
        //创建Enhancer对象(增强器)，也就是cglib中的一个class generator，类似于JDK动态代理的Proxy类
        Enhancer enhancer = new Enhancer();
        //集成被代理类 设置目标类
        enhancer.setSuperclass(this.target.getClass());
        //设置回调对象（继承MethodInterceptor的代理类实例） 这里使用this表示当前对象 与new CglibProxy()效果相同,但是没必要再new一个
        //getInstance方法是在main方法中使用cglibProxy.getInstance(playGames)调用的  this指代调用方法的那个当前对象，就指代cglibProxy
        enhancer.setCallback(this);
//        CglibProxy cglibProxy2=new CglibProxy();
//        enhancer.setCallback(cglibProxy2);
        //这里的creat方法就是正式创建代理类
        System.out.println("this:"+this);
        //System.out.println("new CglibProxy():"+cglibProxy2);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("玩游戏前-开电脑");
        //此处不是反射  并且不能用invoke，会陷入死循环
        Object result = methodProxy.invokeSuper(object, args);
        System.out.println("玩游戏后-关电脑");
        return result;
    }
}
