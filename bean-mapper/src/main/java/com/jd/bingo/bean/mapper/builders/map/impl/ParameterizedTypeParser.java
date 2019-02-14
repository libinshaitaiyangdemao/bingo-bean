package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.CollectionMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 17:40
 * @lastdate:
 */
@Deprecated
public class ParameterizedTypeParser extends TypeParser<ParameterizedType> {

    @Override
    public MapUnit parse(ParameterizedType source, Type target) {
        Class sClass = (Class) source.getRawType();
        if(Collection.class.isAssignableFrom(sClass)){
            CollectionMapUnit mu = new CollectionMapUnit();
            mu.setSouce(source);
            mu.setTarget(target);
            MapUnit element = new MapUnit();
            element.setSouce(source.getActualTypeArguments()[0]);
            mu.setElement(element);
            if(target instanceof Class && ((Class) target).isArray()){
                element.setTarget(((Class) target).getComponentType());
                return mu;
            }else if(target instanceof ParameterizedType && Collection.class.isAssignableFrom((Class<?>) ((ParameterizedType) target).getRawType())){
                element.setTarget(((ParameterizedType) target).getActualTypeArguments()[0]);
                return mu;
            }
        }else if(Map.class.isAssignableFrom(sClass)){
            if(target instanceof ParameterizedType && Map.class.isAssignableFrom((Class<?>) ((ParameterizedType) target).getRawType())){
                MapUnit mu = new MapUnit();
                mu.setSouce(source);
                mu.setTarget(target);
                return mu;
            }
        }else{
            if(target instanceof Class){
                return getMapParser().parse(sClass,target);
            }else if(target instanceof ParameterizedType){
                return getMapParser().parse(sClass,((ParameterizedType) target).getRawType());
            }
        }
        return null;
    }
}
