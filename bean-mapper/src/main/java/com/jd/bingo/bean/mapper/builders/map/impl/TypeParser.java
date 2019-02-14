package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapParser;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 17:40
 * @lastdate:
 */
@Deprecated
public abstract class TypeParser<T extends Type> {

    private MapParser mapParser;

    public MapParser getMapParser() {
        return mapParser;
    }

    public void setMapParser(MapParser mapParser) {
        this.mapParser = mapParser;
    }

    public abstract MapUnit parse(T source, Type target);
}
