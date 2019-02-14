package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MAPMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 16:34
 * @lastdate:
 */
public class MAPMapUnitParseNoder extends BeanMapMapUnitParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        if(!isMap(source)||!isMap(target)){
            return null;
        }
        Type svt = getMapValueType(source,null);
        Type tvt = getMapValueType(target,null);
        if(Object.class.equals(svt)&&!tvt.equals(String.class)&&!svt.equals(tvt)){
            throw new RuntimeException("源MAP的value类型不能确认");
        }
        Class clazs = getMapClass(target);
        if(clazs == null){
            throw new RuntimeException("未找到可实例化的" + target + "的实现");
        }
        MAPMapUnit mu = new MAPMapUnit();
        mu.setTarget(clazs);
        mu.setSouce(source);
        MapUnit keyMU = new MapUnit();
        keyMU.setSouce(getMapKeyType(source,null));
        keyMU.setTarget(getMapKeyType(target,String.class));
        if(keyMU.getSouce().equals(Object.class)&&!keyMU.getTarget().equals(keyMU.getSouce())&&!keyMU.getTarget().equals(String.class)){
            throw new RuntimeException("MAP的KEY类型不能确认");
        }
        MapUnit valueMU = new MapUnit();
        valueMU.setTarget(tvt);
        valueMU.setSouce(svt);
        return mu;
    }
}
