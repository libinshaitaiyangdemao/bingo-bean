package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.CollectionMapUnit;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 17:40
 * @lastdate:
 */
public class GenericArrayTypeParser extends TypeParser<GenericArrayType> {

    @Override
    public MapUnit parse(GenericArrayType source, Type target) {
        CollectionMapUnit mu = new CollectionMapUnit();
        mu.setSouce(source);
        mu.setTarget(target);
        MapUnit element = new MapUnit();
        mu.setElement(element);
        mu.setSouce(source.getGenericComponentType());
        if(target instanceof GenericArrayType){
            mu.setTarget(((GenericArrayType) target).getGenericComponentType());
        }else if(target instanceof ParameterizedType){
            Type[] types = ((ParameterizedType) target).getActualTypeArguments();
            if(types == null || types.length != 1){
                throw new RuntimeException("解析失败，Target ParameterizedType.length != 1");
            }
            mu.setTarget(types[0]);
        }else if(target instanceof Class && ((Class) target).isArray()){
            mu.setTarget(((Class) target).getComponentType());
        }else{
            return null;
        }
        return mu;
    }
}
