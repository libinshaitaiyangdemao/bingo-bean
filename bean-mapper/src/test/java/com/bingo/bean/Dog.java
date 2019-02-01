package com.bingo.bean;

import com.jd.bingo.bean.mapper.annotation.Expressions;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/28 11:26
 * @lastdate:
 */
public class Dog {
    private String name;
    private Integer age;
    private List<String> friends;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getDogName() {
        return name;
    }

    @Expressions("name")
    public void setDogName(String name) {
        this.name = name;
    }

    public Integer getDogAge() {
        return age;
    }

    @Expressions("age")
    public void setDogAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", friends=" + friends +
                '}';
    }
}
