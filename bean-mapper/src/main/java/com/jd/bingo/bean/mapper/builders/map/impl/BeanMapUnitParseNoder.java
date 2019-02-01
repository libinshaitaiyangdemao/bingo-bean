package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.annotation.Expressions;
import com.jd.bingo.bean.mapper.builders.map.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;
import com.jd.bingo.bean.mapper.utils.DefiniteType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 在解析链中，该类放最后，不考虑其他情况，认为到该类的都解析
 *
 * @author: libin29
 * @createdate: 2019/1/31 17:47
 * @lastdate:
 */
public class BeanMapUnitParseNoder implements ChainMapParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        if(!source.equals(target) && source.equals(Object.class) && !target.equals(String.class)){
            return null;
        }
        BeanMapUnit bmu = new BeanMapUnit();
        bmu.setSouce(source);
        bmu.setTarget(target);
        Map<String,MapUnit> properties = readSourceProperties(source);
        List<MapUnit> props = mapTargetProperties(target,properties);
        if(properties != null && !properties.isEmpty()){
            bmu.setProperties(props.stream().filter(m->m.getSouce()!=null&&m.getTarget()!=null&&m.getGetMethodName()!=null&&m.getSetMethodName()!=null).collect(Collectors.toList()));
        }
        return bmu;
    }

    private Map<String,MapUnit> readSourceProperties(Type source){
        Map<Class,List<DefiniteType>> cms = confirmClass(source);
        if(cms == null){
            return null;
        }
        Class clazs = cms.keySet().iterator().next();
        List<DefiniteType> dts = cms.get(clazs);
        Map<String,MapUnit> mus = new HashMap<>();
        Map<Class, List<DefiniteType>> confirms = BeanUtil.confirmSupperParameterizedActualTypeArguments(clazs,dts);
        Map<Class, List<Field>> fieldMap = BeanUtil.getExtentsFields(clazs, f->true);
        if(!fieldMap.isEmpty()){
            fieldMap.forEach((k,v)->{
                if(!v.isEmpty()){
                    v.forEach(f->{
                        Method getMethod = BeanUtil.getGetMethod(f, clazs);
                        if(getMethod != null){
                            MapUnit mu = new MapUnit();
                            mu.setGetMethodName(getMethod.getName());
                            mu.setName(getMapFieldName(f));
                            Type s = BeanUtil.convertType(BeanUtil.confirmDefiniteType(f.getGenericType(),confirms.get(k)));
                            mu.setSouce(s);
                            mus.put(mu.getName(),mu);
                        }
                    });
                }
            });
        }
        Map<Class, List<Method>> methodMap = BeanUtil.getExtentsMethods(clazs,m->{
            Expressions expressions = m.getAnnotation(Expressions.class);
            if(expressions == null){
                return false;
            }
            if(m.getParameterTypes().length!=0){
                return false;
            }
            if(m.getReturnType().equals(Void.TYPE)){
                return false;
            }
            if ((!Modifier.isPublic(m.getModifiers()) || !Modifier.isPublic(m.getDeclaringClass().getModifiers())
                    || Modifier.isFinal(m.getModifiers())) && !m.isAccessible()) {
                return false;
            }
            return true;
        });
        if(!methodMap.isEmpty()){
            methodMap.forEach((k,v)->{
                if(!v.isEmpty()){
                    v.forEach(m->{
                        String name = getMapMethodName(m);
                        MapUnit mu = mus.get(name);
                        if(mu == null){
                            mu = new MapUnit();
                            mu.setName(name);
                            mus.put(name,mu);
                        }
                        mu.setGetMethodName(m.getName());
                        Type s = BeanUtil.convertType(BeanUtil.confirmDefiniteType(m.getGenericReturnType(),confirms.get(k)));
                        mu.setSouce(s);
                    });
                }
            });
        }
        return mus;
    }
    private List<MapUnit> mapTargetProperties(Type target,Map<String,MapUnit> mus){
        if(mus == null || mus.isEmpty()){
            return null;
        }
        Map<Class,List<DefiniteType>> cms = confirmClass(target);
        if(cms == null){
            return null;
        }
        Class clazs = cms.keySet().iterator().next();
        List<DefiniteType> dts = cms.get(clazs);
        List<MapUnit> result = new ArrayList<>();
        Map<Class, List<DefiniteType>> confirms = BeanUtil.confirmSupperParameterizedActualTypeArguments(clazs,dts);
        Map<Class, List<Field>> fieldMap = BeanUtil.getExtentsFields(clazs, f->true);
        if(!fieldMap.isEmpty()){
            fieldMap.forEach((k,v)->{
                if(!v.isEmpty()){
                    v.forEach(f->{
                        String name = getMapFieldName(f);
                        MapUnit mu = mus.get(name);
                        if(mu != null){
                            Method setMethod = BeanUtil.getSetMethod(f, clazs);
                            if(setMethod != null){
                                mu.setSetMethodName(setMethod.getName());
                                Type s = BeanUtil.convertType(BeanUtil.confirmDefiniteType(f.getGenericType(),confirms.get(k)));
                                mu.setTarget(s);
                                result.add(mu);
                            }
                        }
                    });
                }
            });
        }
        Map<Class, List<Method>> methodMap = BeanUtil.getExtentsMethods(clazs,m->{
            Expressions expressions = m.getAnnotation(Expressions.class);
            if(expressions == null){
                return false;
            }
            if(m.getParameterTypes().length != 1){
                return false;
            }
            if(!m.getReturnType().equals(Void.TYPE)){
                return false;
            }
            if ((!Modifier.isPublic(m.getModifiers()) || !Modifier.isPublic(m.getDeclaringClass().getModifiers())
                    || Modifier.isFinal(m.getModifiers())) && !m.isAccessible()) {
                return false;
            }
            return true;
        });
        if(!methodMap.isEmpty()){
            methodMap.forEach((k,v)->{
                if(!v.isEmpty()){
                    v.forEach(m->{
                        String name = getMapMethodName(m);
                        MapUnit mu = mus.get(name);
                        if(mu != null){
                            mu.setSetMethodName(m.getName());
                            Type s = BeanUtil.convertType(BeanUtil.confirmDefiniteType(m.getGenericParameterTypes()[0],confirms.get(k)));
                            mu.setTarget(s);
                            if(!result.contains(mu)){
                                result.add(mu);
                            }
                        }
                    });
                }
            });
        }
        return result;
    }

    private Map<Class,List<DefiniteType>> confirmClass(Type type){
        List<DefiniteType> dts = null;
        Class clazs = null;
        if(type instanceof Class){
            clazs = (Class) type;
        }else if(type instanceof ParameterizedType){
            clazs = (Class)((ParameterizedType) type).getRawType();
            dts = BeanUtil.getParameterizedActualTypeArguments(clazs,((ParameterizedType) type).getActualTypeArguments());
        }else{
            return null;
        }
        Map<Class,List<DefiniteType>> entry = new HashMap<>();
        entry.put(clazs,dts);
        return entry;
    }
    private String getMapFieldName(Field field){
        Expressions expressions = field.getAnnotation(Expressions.class);
        if(expressions != null && expressions.value() != null && !expressions.value().trim().isEmpty()){
            return expressions.value().trim();
        }
        return field.getName();
    }
    private String getMapMethodName(Method method){
        Expressions expressions = method.getAnnotation(Expressions.class);
        if(expressions != null && expressions.value() != null && !expressions.value().trim().isEmpty()){
            return expressions.value().trim();
        }
        return method.getName();
    }
}
