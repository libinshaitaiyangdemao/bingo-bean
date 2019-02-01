package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapUnit;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/31 9:33
 * @lastdate:
 */
public class BaseMapUnitParseNoder implements ChainMapParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        if (target.equals(String.class)||(source instanceof Class && ParseUtil.isBaseClass((Class) source) && target instanceof Class && ParseUtil.isBaseClass((Class) target))) {
            MapUnit mu = new MapUnit();
            mu.setTarget(target);
            mu.setSouce(source);
            return mu;
        }
        return null;
    }
}
