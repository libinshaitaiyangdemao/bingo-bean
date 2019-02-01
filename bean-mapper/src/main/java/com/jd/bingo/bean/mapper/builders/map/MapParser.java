package com.jd.bingo.bean.mapper.builders.map;

import com.jd.bingo.bean.mapper.builders.map.impl.ClassParser;
import com.jd.bingo.bean.mapper.builders.map.impl.GenericArrayTypeParser;
import com.jd.bingo.bean.mapper.builders.map.impl.ParameterizedTypeParser;
import com.jd.bingo.bean.mapper.builders.map.impl.TypeParser;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 13:55
 * @lastdate:
 */
public interface MapParser {


    /**
     * 解析服务,type几种实现为ParameterizedType，GenericArrayType，WildcardType(出现时再度解析，所以到不了该步骤)，class，TypeVariable(出现时再度解析，所以到不了该步骤)
     *
     * @param source
     * @param target
     * @return
     */
    MapUnit parse(Type source, Type target);

}
