package com.wxy.wjl.testspringboot2.domain;

import com.wxy.wjl.testspringboot2.domain.cusannotation.Person;
import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String id;
    private String classid;
    private String name;
    private String MAC_address;
    private String e_mail;
    private String image;
    private String phone;
    private int sId;
    private Integer sId2;
    private Person person;
    private List<String> test;
    private List<Person> test2;

}
