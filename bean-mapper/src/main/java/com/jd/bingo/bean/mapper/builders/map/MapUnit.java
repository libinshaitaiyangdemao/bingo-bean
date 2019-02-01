package com.jd.bingo.bean.mapper.builders.map;

import java.lang.reflect.Type;

public class MapUnit{

    private String name;

    private Type souce;

    private Type target;

    private String getMethodName;

    private String setMethodName;


    public String getGetMethodName() {
        return getMethodName;
    }

    public void setGetMethodName(String getMethodName) {
        this.getMethodName = getMethodName;
    }

    public String getSetMethodName() {
        return setMethodName;
    }

    public void setSetMethodName(String setMethodName) {
        this.setMethodName = setMethodName;
    }

    public Type getSouce() {
        return souce;
    }

    public void setSouce(Type souce) {
        this.souce = souce;
    }

    public Type getTarget() {
        return target;
    }

    public void setTarget(Type target) {
        this.target = target;
    }

    public boolean sameType(){
        return souce.equals(target);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}