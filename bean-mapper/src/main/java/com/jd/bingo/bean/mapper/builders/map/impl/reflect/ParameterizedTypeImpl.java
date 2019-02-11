package com.jd.bingo.bean.mapper.builders.map.impl.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/11 10:59
 * @lastdate:
 */
public class ParameterizedTypeImpl implements ParameterizedType{

    private Type rawType;
    private Type[] actualTypeArguments;
    private Type ownerType;

    public ParameterizedTypeImpl(Type rawType, Type[] actualTypeArguments, Type ownerType) {
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType = ownerType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }
}
