package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.CollectionMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import static com.jd.bingo.bean.mapper.builders.map.impl.ParseUtil.mapProperties;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 17:40
 * @lastdate:
 */
@Deprecated
public class ClassParser extends TypeParser<Class> {

    @Override
    public MapUnit parse(Class source, Type target) {
        if (ParseUtil.isBaseClass(source)) {
            if (target instanceof Class && ParseUtil.isBaseClass((Class) target)) {
                MapUnit mu = new MapUnit();
                mu.setTarget(target);
                mu.setSouce(source);
                return mu;
            }
        } else if (String.class.equals(target)) {
            MapUnit mu = new MapUnit();
            mu.setTarget(target);
            mu.setSouce(source);
            return mu;
        } else if (source.isArray()) {
            if ((target instanceof Class && ((Class) target).isArray()) || (target instanceof ParameterizedType && Collection.class.isAssignableFrom((Class<?>) ((ParameterizedType) target).getRawType()))) {
                CollectionMapUnit mu = new CollectionMapUnit();
                mu.setSouce(source);
                MapUnit element = new MapUnit();
                mu.setElement(element);
                element.setSouce(source.getComponentType());
                mu.setTarget(target);
                if (target instanceof Class) {
                    element.setTarget(((Class) target).getComponentType());
                } else {
                    Type[] types = ((ParameterizedType)target).getActualTypeArguments();
                    if(types == null && types.length != 1){
                        throw new RuntimeException("解析错误，ActualTypeArguments.length != 1");
                    }
                    element.setTarget(types[0]);
                }
                return mu;
            }
        } else if (target instanceof Class || target instanceof ParameterizedType) {
            //普通
            Class tc = null;
            if (target instanceof Class) {
                tc = (Class) target;
            } else {
                tc = (Class) ParameterizedType.class.cast(target).getRawType();
            }
            BeanMapUnit mu = new BeanMapUnit();
            mu.setSouce(source);
            mu.setTarget(tc);
            mu.setProperties(mapProperties(source, tc));
            return mu;
        }
        return null;
    }


}
