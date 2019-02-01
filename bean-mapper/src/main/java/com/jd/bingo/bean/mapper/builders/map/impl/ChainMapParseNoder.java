package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapUnit;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/30 18:08
 * @lastdate:
 */
public interface ChainMapParseNoder {

    /**
     * 解析方法，可解析则解析，解析不了则返回空
     *
     * @param source
     * @param target
     * @return
     */
    MapUnit parse(Type source, Type target);
}
