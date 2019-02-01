package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.CollectionMapUnit;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;
import com.jd.bingo.bean.mapper.utils.DefiniteType;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.jd.bingo.bean.mapper.utils.BeanUtil.convertType;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/31 10:42
 * @lastdate:
 */
public class CollectionMapUnitParseNoder implements ChainMapParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        Type eleSource = getElementType(source);
        if(eleSource != null){
            Type eleTarg = getElementType(target);
            if(eleTarg != null){
                if(eleSource.equals(eleTarg) || !eleSource.equals(Object.class)){
                    CollectionMapUnit mu = new CollectionMapUnit();
                    mu.setTarget(target);
                    mu.setSouce(source);
                    mu.setElement(new MapUnit());
                    mu.getElement().setSouce(eleSource);
                    mu.getElement().setTarget(eleTarg);
                    return mu;
                }
            }
        }
        return null;
    }

    private Type getElementType(Type type){
        Type result = getCollectionParameterizedRaw(type);
        if(result == null){
            result = getParameterizedRaw(type);
        }
        if(result == null){
            result = getArrayComponentType(type);
        }
        if(result == null){
            result = getGenericArrayComponentType(type);
        }
        return result;
    }
    private Type getCollectionParameterizedRaw(Type type){
        if(type instanceof Class && Collection.class.isAssignableFrom((Class) type)){
            Type t = getCollectionClassParameterizedActualTypeArgument((Class) type);
            return convertType(t);
        }
        return null;
    }

    private Type getArrayComponentType(Type type){
        if(type instanceof Class && ((Class) type).isArray()){
            return ((Class) type).getComponentType();
        }
        return null;
    }

    private Type getGenericArrayComponentType(Type type){
        if(type instanceof GenericArrayType){
            return ((GenericArrayType) type).getGenericComponentType();
        }
        return null;
    }

    private Type getCollectionClassParameterizedActualTypeArgument(Class clazs){
        List<Class> branch = BeanUtil.getExtendsBranch(clazs,Collection.class);
        Map<Class,List<DefiniteType>> map = BeanUtil.getClassParameterizedActualTypeArguments(branch);
        if(map != null && !map.isEmpty()){
            List<DefiniteType> dts = map.get(Collection.class);
            if(dts != null && dts.size() == 1){
                Type t = dts.get(0).getDefinited();
                return t;
            }
        }
        return null;
    }
    private Type getParameterizedRaw(Type type){
        if(type instanceof ParameterizedType){
            Type t = ((ParameterizedType) type).getRawType();
            if(t instanceof Class && Collection.class.isAssignableFrom((Class<?>) t)){
                Type argument = getCollectionClassParameterizedActualTypeArgument((Class) t);
                if(argument != null){
                    List<DefiniteType> dts = BeanUtil.getParameterizedActualTypeArguments((Class) t,((ParameterizedType) type).getActualTypeArguments());
                    argument = BeanUtil.confirmDefiniteType(argument,dts);
                    return convertType(argument);
                }
                return Object.class;

            }
        }
        return null;
    }


}
