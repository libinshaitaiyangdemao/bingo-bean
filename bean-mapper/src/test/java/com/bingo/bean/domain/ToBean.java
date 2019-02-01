package com.bingo.bean;

import java.util.Date;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 14:31
 * @lastdate:
 */
public class ToBean {
    private long age;
    private String name;
    private long birthday;
    private String address;
    private long money;
    private int height;
    private Dog pet;

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Dog getPet() {
        return pet;
    }

    public void setPet(Dog pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "ToBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", money=" + money +
                ", height=" + height +
                ", pet=" + pet +
                '}';
    }
}
