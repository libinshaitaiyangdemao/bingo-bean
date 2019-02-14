package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 16:19
 * @lastdate:
 */
public class MapBeanMapUnitParseNoder extends BeanMapMapUnitParseNoder {
    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        if(!isBean(target)||!isMap(source)){
            return null;
        }

        Type vt = getMapValueType(target,null);
        if(vt.equals(Object.class)){
            throw new RuntimeException("不能确定MAP的VALUE类型，无法映射");
        }
        BeanMapMapUnit mu = new BeanMapMapUnit();
        mu.setTarget(getBeanClass(target));
        mu.setSouce(source);
        MapUnit keyMU = new MapUnit();
        keyMU.setSouce(getMapKeyType(source,String.class));
        keyMU.setTarget(String.class);
        Map<String,MapUnit> map = readSourceProperties(target);
        if(map!=null && !map.isEmpty()){
            map.forEach((n,u)->{
                u.setTarget(u.getSouce());
                u.setSouce(vt);
            });
            mu.setProperties(new ArrayList<>(map.values()));
        }
        return mu;
    }
}
