package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 15:05
 * @lastdate:
 */
public class BeanMapMapUnitParseNoder extends BeanMapUnitParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        if(!isBean(source)||!isMap(target)){
            return null;
        }
        Class clazs = getMapClass(target);
        if(clazs == null){
            throw new RuntimeException("未找到可实例化的" + target + "的实现");
        }
        BeanMapMapUnit mu = new BeanMapMapUnit();
        mu.setTarget(clazs);
        mu.setSouce(getBeanClass(source));
        MapUnit keyMU = new MapUnit();
        keyMU.setSouce(String.class);
        keyMU.setTarget(getMapKeyType(target,String.class));
        Map<String,MapUnit> map = readSourceProperties(source);
        if(map!=null && !map.isEmpty()){
            Type vt = getMapValueType(target,null);
            map.forEach((n,u)->{
                if(Object.class.equals(vt)){
                    if(u.getSouce() instanceof Class && ParseUtil.isBaseClass((Class) u.getSouce())){
                        u.setTarget(BeanUtil.getWrapperClass((Class) u.getSouce()));
                    }else{
                        u.setTarget(Map.class);
                    }
                }else{
                    u.setTarget(vt);
                }
            });
            mu.setProperties(new ArrayList<>(map.values()));
        }
        return mu;
    }

    protected Class getMapClass(Type type){
        Class clazs = getBeanClass(type);
        if(clazs.isInterface()){
            if(clazs.isAssignableFrom(HashMap.class)){
                return HashMap.class;
            }
            return null;
        }
        return clazs;
    }

    /**
     * 值的类型
     *
     * @param type
     * @return
     */
    protected Type getMapValueType(Type type,Type dt){
        if(type instanceof ParameterizedType){
            Type t = ((ParameterizedType) type).getActualTypeArguments()[1];
            if(!Object.class.equals(t)){
                return t;
            }
        }
        if(dt != null){
            return dt;
        }
        return type;
    }

    /**
     * 键的类型
     *
     * @param type
     * @param dt 默认值
     * @return
     */
    protected Type getMapKeyType(Type type,Type dt){
        if(type instanceof ParameterizedType){
            Type t = ((ParameterizedType) type).getActualTypeArguments()[0];
            if(!Object.class.equals(t)){
                return t;
            }
        }
        if(dt != null){
            return dt;
        }
        return type;
    }
    protected Class getBeanClass(Type type){
        if(type instanceof Class){
            return (Class) type;
        }else if(type instanceof ParameterizedType){
            return (Class) ((ParameterizedType) type).getRawType();
        }
        return null;
    }
    /**
     * 判断是否是MAP
     *
     * @param type
     * @return
     */
    protected boolean isMap(Type type){
        if(type instanceof Class){
            return Map.class.isAssignableFrom((Class<?>) type);
        }else if(type instanceof ParameterizedType){
            return Map.class.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType());
        }
        return false;
    }

    /**
     * 判断是否是可解析的BeanClass
     *
     * @param type
     * @return
     */
    protected boolean isBean(Type type){
        if(!isMap(type)){
            if(type instanceof Class){
                return !ParseUtil.isBaseClass((Class) type);
            }
        }
        return false;
    }
}
