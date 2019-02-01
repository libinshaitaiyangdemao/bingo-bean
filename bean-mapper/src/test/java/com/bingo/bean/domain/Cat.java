package com.bingo.bean;

import com.jd.bingo.bean.mapper.annotation.Expressions;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/28 11:26
 * @lastdate:
 */
public class Cat {
    private String name;
    private int age;
    private Integer[] friends;

    public Integer[] getFriends() {
        return friends;
    }

    public void setFriends(Integer[] friends) {
        this.friends = friends;
    }

//    @Expressions("name")
//    public String getCatName() {
//        return name;
//    }
//
//    public void setCatName(String name) {
//        this.name = name;
//    }
//
//    @Expressions("age")
//    public int getCatAge() {
//        return age;
//    }
//
//    public void setCatAge(int age) {
//        this.age = age;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
