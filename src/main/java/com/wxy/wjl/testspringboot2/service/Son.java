package com.wxy.wjl.testspringboot2.service;

/**
 *
 * 测试hashcode与equals
 */
public class Son implements Cloneable{

    public Son(){
    }
    public Son(String name){
        this.name=name;
    }
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    private boolean good;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }

    @Override
    public boolean equals(Object object){
        Son son=(Son)object;
        return this.name.equals(son.getName());
    }

    public Son clone(){
        Son son=null;
        try{
            son=(Son)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return son;
    }

}
