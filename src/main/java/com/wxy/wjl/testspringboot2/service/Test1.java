package com.wxy.wjl.testspringboot2.service;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.domain.cusannotation.Person;
import com.wxy.wjl.testspringboot2.utils.CommonUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Test1 {


    /**
     * 迭代器 Iterator
     */
    @Test
    public void test1(){

        List<String> list=new ArrayList();
        list.add("b");
        list.add("a");



        Map<String,String> map=new HashMap<>();
        map.put("f","3");
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        map.put("d","3");
        map.put("e","3");
        //遍历list
        Iterator<String> iterator=list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        //遍历map
        Iterator<Map.Entry<String,String>> iterator1=map.entrySet().iterator();
        while (iterator1.hasNext()){
            Map.Entry entry=iterator1.next();
            System.out.println(entry.getKey() + "  "+entry.getValue());
        }

        //使用for循环遍历map
        for(Map.Entry<String,String> enty :map.entrySet()){
            System.out.println(enty.getKey() + "  "+enty.getValue());
        }

    }

    @Test
    public void test2(){
        Set<Son> set = new HashSet<Son>();
        Son p1 = new Son("唐僧");
        Son p2 = new Son("孙悟空");
        Son p3 = new Son("猪八戒");
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("总共有:"+set.size()+" 个元素!"); //结果：总共有:3 个元素!
        p3.setName("aaa"); //修改p3的年龄,此时p3元素对应的hashcode值发生改变
        set.remove(p3); //此时remove不掉，造成内存泄漏
        set.add(p3); //重新添加，居然添加成功
        System.out.println("总共有:"+set.size()+" 个元素!"); //结果：总共有:4 个元素!
    }


    /**
     * 测试json解析
     */
    @Test
    public void test3(){
        Son p1 = new Son("僧");
        p1.setAge(18);
        p1.setGood(true);
        //String jsonStr= JSON.toJSONString(p1);
        String jsonString="{}";
        p1= JSON.parseObject(jsonString,Son.class);
        System.out.println("name："+p1.getName()+" firstName:"+p1.getFirstName()+" age:"+p1.getAge()+" good:"+p1.isGood());

    }

    /**
     * 测试类加载器
     */
    @Test
    public void test4() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }


    @Test
    public void test5(){
        String s1 = "abc";
        String s2 = "a";
        String s3 = "bc";
        String s4 = s2 + s3;
        System.out.println(s1 == s4);

    }
    @Test
    public void test6() throws Exception{
        //获取Son类的Class对象
        Class clazz=Class.forName("com.wjl.wxy.learn.practice.api.service.Son");
        //获取 Person 类的所有方法信息
        //获取 Person 类的所有成员属性信息
        Field[] field=clazz.getDeclaredFields();
        for(Field f:field){
            System.out.println(f.toString()+ "    " +f.getAnnotatedType());
        }

    }

    /**
     * 测试字符串所在位置以及具体值
     * @throws Exception
     */
    @Test
    public void test7() throws Exception{
        //eg1
        String a = "a1";
        String a1 = "a" + 1;
        System.out.println(a == a1); //true
        //eg2
        String b = "b1";
        int bb = 1;
        String b1 = "b" + bb;
        System.out.println(b == b1); //false
        //eg3
        String c = "c1";
        final int cc = 1;
        String c1 = "c" + cc;
        System.out.println(c == c1); //true
        //eg4  true
        String d = "d1";
        final int dd = 1;
        String d1 = "d" + dd;
        System.out.println(d == d1);
        //eg5  false
        String e = "e1";
        int ee = 1;
        final String e1 = "e" + ee;
        System.out.println(e == e1);
        //eg6  false
        String f = "f1";
        String ff = "1";
         String f1 = "f" + ff;
        System.out.println(f == f1);
    }

    /**
     * 测试在foreach中删除list元素会有什么问题
     * @throws Exception
     */
    @Test
    public void test8() throws Exception{
        List<Person> list=new ArrayList();
        Person p1=new Person();
        p1.setIdInfo("1");
        Person p2=new Person();
        p2.setIdInfo("2");
        Person p3=new Person();
        p3.setIdInfo("3");
        list.add(p1);
        list.add(p2);
        list.add(p3);
        for(Person p: list){
            if(p.getIdInfo().equals("1")){
                list.remove(p);
            } }
    }

    /**
     * 测试枚举转字符串
     * @throws Exception
     */
    @Test
    public void test9() throws Exception{
        String sqlStr= CommonUtils.busMngCdConvert("customerManagement");
        System.out.println(sqlStr);

    }


    /**
     * 测试日期格式转换
     * @throws Exception
     */
    @Test
    public void test10() throws Exception{
        String date="20200101";
        System.out.println(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6));
        String dateTime="063200";
        System.out.println(dateTime.substring(0,2)+":"+dateTime.substring(2,4)+":"+dateTime.substring(4)) ;

        System.out.println(new SimpleDateFormat("yyyy-MM-dd" ).format(new SimpleDateFormat("yyyyMMdd").parse(date)));
        System.out.println( new SimpleDateFormat("HH:mm:ss" ).format(new SimpleDateFormat("HHmmss").parse(dateTime)));
        BigDecimal amount =new BigDecimal("00");
        System.out.println(new DecimalFormat("0.00").format(amount));
        LocalDate localDate=LocalDate.now();
        System.out.println(localDate);
    }


    /**
     *有N个鸡蛋，要依次投放到如下BOX中，
     * 【1】，【2】，【4】，【8】，【16】。。。。【？】
     * 请求出最后一个box有多少个鸡蛋？
     * @throws Exception
     */
    @Test
    public void test11() throws Exception{

        int x=sub(20,1);
        System.out.println(x);
    }

    public int sub(int n,int box){
        if(n < box){
            return n;
        }else if(n == box){
            return  0;
        }else{
            n=n-box;
            box=box*2;
            return sub(n,box);
        }
    }
}