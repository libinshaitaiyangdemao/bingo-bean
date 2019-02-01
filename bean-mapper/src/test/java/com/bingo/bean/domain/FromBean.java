package com.bingo.bean.domain;

import com.jd.bingo.bean.mapper.annotation.Expressions;

import java.beans.Transient;
import java.util.Date;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 14:27
 * @lastdate:
 */
public class FromBean {
    private int age;
    private String name;
    private Date birthday;
    private String address;
    private double money;
    private float height;
    private Cat pet;
    private ParameterizedBean<Cat> pb;

    public ParameterizedBean<Cat> getPb() {
        return pb;
    }

    public void setPb(ParameterizedBean<Cat> pb) {
        this.pb = pb;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Transient
    @Expressions("birthday")
    public long getBirthdayTime(){
        return birthday.getTime();
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Cat getPet() {
        return pet;
    }

    public void setPet(Cat pet) {
        this.pet = pet;
    }
}
