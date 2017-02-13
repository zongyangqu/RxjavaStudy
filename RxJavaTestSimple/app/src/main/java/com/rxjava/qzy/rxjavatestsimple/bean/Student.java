package com.rxjava.qzy.rxjavatestsimple.bean;

import java.util.List;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/13
 *
 * 类描述：
 */

public class Student {

    public String name;

    public List<Course> courseList;

    public Student(){

    }
    public Student(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
