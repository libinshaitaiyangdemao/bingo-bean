package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.annotation.Expressions;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/25 10:13
 * @lastdate:
 */
public class ParseUtil {
    private static List<Class> baseClasses;
    static {
        baseClasses = new ArrayList<>();
        baseClasses.add(String.class);
        baseClasses.add(Integer.class);
        baseClasses.add(Long.class);
        baseClasses.add(Float.class);
        baseClasses.add(Double.class);
        baseClasses.add(int.class);
        baseClasses.add(long.class);
        baseClasses.add(float.class);
        baseClasses.add(double.class);
        baseClasses.add(byte.class);
        baseClasses.add(Byte.class);
        baseClasses.add(char.class);
        baseClasses.add(Character.class);
        baseClasses.add(short.class);
        baseClasses.add(Short.class);
        baseClasses.add(Date.class);
    }
    public static boolean isBaseClass(Class clazs){
        return clazs.isEnum() || baseClasses.contains(clazs);
    }

    /**
     * 映射子属性
     *
     * @param source
     * @param target
     * @return
     */
    public static List<MapUnit> mapProperties(Class source, Class target){
        List<Field> fields = BeanUtil.getFields(source);
        Map<String,MapUnit> map = new HashMap<>();
        if(fields != null && !fields.isEmpty()){
            for (Field f: fields){
                MapUnit mu = new MapUnit();
                String name = getMapFieldName(f);
                mu.setName(name);
                mu.setSouce(f.getGenericType());
                Method method = BeanUtil.getGetMethod(f,source);
                if(method == null){
                    continue;
                }
                mu.setGetMethodName(method.getName());
                map.put(name,mu);
            }
        }

        //解析method
        List<Method> methods = BeanUtil.getMethodsByAnnotation(source,Expressions.class);
        if(methods != null && !methods.isEmpty()){
            for (Method m:methods){
                Expressions expressions = m.getAnnotation(Expressions.class);
                String name = expressions.value();
                MapUnit mu = map.get(name);
                if(mu == null){
                    mu = new MapUnit();
                    mu.setName(name);
                    map.put(name,mu);
                }
                mu.setGetMethodName(m.getName());
                mu.setSouce(m.getGenericReturnType());
            }
        }
        //对应target
        List<MapUnit> mus = new ArrayList<>();
        fields = BeanUtil.getFields(target);
        if(fields != null && !fields.isEmpty()){
            for (Field f: fields){
                String name = getMapFieldName(f);
                MapUnit mu = map.get(name);
                if(mu == null){
                    continue;
                }
                mu.setTarget(f.getGenericType());
                Method method = BeanUtil.getSetMethod(f,target);
                if(method != null){
                    mu.setSetMethodName(method.getName());
                    mus.add(mu);
                }
            }
        }
        //对应method
        methods = BeanUtil.getMethodsByAnnotation(target,Expressions.class);
        if(methods != null && !methods.isEmpty()){
            for (Method m:methods){
                String name = m.getAnnotation(Expressions.class).value();
                MapUnit mu = map.get(name);
                if(mu == null){
                    continue;
                }
                mu.setSetMethodName(m.getName());
                Type[] types = m.getGenericParameterTypes();
                if(types == null || types.length != 1){
                    throw new RuntimeException("标记的 SET 方法不合理，参数个数不是 1");
                }
                mu.setTarget(types[0]);
                if(!mus.contains(mu)){
                    mus.add(mu);
                }
            }
        }
        return mus;
    }

    private static String getMapFieldName(Field field){
        Expressions expressions = field.getAnnotation(Expressions.class);
        if(expressions != null){
            return expressions.value();
        }
        return field.getName();
    }
}
