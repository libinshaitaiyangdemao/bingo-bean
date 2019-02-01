package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapUnit;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 17:40
 * @lastdate:
 */
public class WildCardTypeParser extends TypeParser<WildcardType> {

    @Override
    public MapUnit parse(WildcardType source, Type target) {
        return null;
    }
}
