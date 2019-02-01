package com.jd.bingo.bean.mapper.utils;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/31 15:55
 * @lastdate:
 */
public class DefiniteType {
    private TypeVariable<Class<?>> typeVariable;
    private Type definited;

    public DefiniteType() {
    }

    public DefiniteType(TypeVariable<Class<?>> typeVariable, Type definited) {
        this.typeVariable = typeVariable;
        this.definited = definited;
    }

    public TypeVariable<Class<?>> getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(TypeVariable<Class<?>> typeVariable) {
        this.typeVariable = typeVariable;
    }

    public Type getDefinited() {
        return definited;
    }

    public void setDefinited(Type definited) {
        this.definited = definited;
    }
}
