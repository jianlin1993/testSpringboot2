package com.wxy.wjl.testspringboot2.service;


import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * school,lesson类如下
 * // 学校数据
 * Class school{
 * int shoolid;
 * String schoolname; //学校名字
 * };
 * //上课数据
 * Class lesson{
 * int schoolid;
 * int studentCount; //学生数量
 * }
 * 分别有2个list：List<school> schools;List<lesson> lessons，存放school与lesson数据，lessions含有多条相同schoolid的数据，
 * 现在需要统计每个学校上课的学生总数，并且将学校数据按照上课学生总数降序排列，将上课学生总数>1000的学校名字存放到一个list中。
 */
public class SchoolTest {

    @Data
    static  class school{
        public school(Integer shoolid,String schoolname){
            this.setShoolid(shoolid);
            this.setSchoolname(schoolname);
        }
        int shoolid;
        String schoolname; //学校名字
    };
    //上课数据
    @Data
    static class lesson{
        public lesson(Integer schoolid,Integer studentCount){
            this.schoolid=schoolid;
            this.studentCount=studentCount;
        }
        int schoolid;
        int studentCount; //学生数量
    }

    public static void main(String[] args) {
        List<school> schools=new ArrayList<>();
        List<lesson> lessons=new ArrayList<>();
        school school1=new school(1,"school1");
        school school2=new school(2,"school2");
        school school3=new school(3,"school3");
        school school4=new school(4,"school4");
        schools.add(school1);
        schools.add(school2);
        schools.add(school3);
        schools.add(school4);
        lesson lesson1=new lesson(1,1);
        lesson lesson2=new lesson(1,400);
        lesson lesson3=new lesson(1,600);
        lesson lesson4=new lesson(2,57);
        lesson lesson5=new lesson(2,68);
        lesson lesson6=new lesson(3,1500);
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);
        lessons.add(lesson6);
        setList(schools,lessons);

    }

    public static void setList(List<school> schools,List<lesson> lessons){
        Map<Integer, String> schoolMap = schools.stream().collect(Collectors.toMap(school::getShoolid, school::getSchoolname));
        Map<Integer,Integer> lessonMap=new HashMap<>(lessons.size());
        List<String> schoolNameList=new ArrayList<>();
        for(lesson lesson:lessons){
            if(lessonMap.containsKey(lesson.getSchoolid())){
                lessonMap.put(lesson.getSchoolid(),lesson.getStudentCount()+lessonMap.get(lesson.getSchoolid()));
            }else{
                lessonMap.put(lesson.getSchoolid(),lesson.getStudentCount());
            }
        }
        List<Map.Entry<Integer,Integer>> entryList=new ArrayList<>(lessonMap.entrySet());
        Collections.sort(entryList, (o1, o2) -> {
            // 降序排序
            return o2.getValue()-o1.getValue();
        });
        for(Map.Entry<Integer,Integer> entry:entryList){
            if(entry.getValue() > 1000){
                schoolNameList.add(schoolMap.get(entry.getKey()));
            }else{
                break;
            }
        }
        for (String s:schoolNameList){
            System.out.println(s);
        }
    }
}
