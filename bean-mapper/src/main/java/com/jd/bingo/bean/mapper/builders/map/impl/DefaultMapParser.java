package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapParser;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.builders.map.impl.ClassParser;
import com.jd.bingo.bean.mapper.builders.map.impl.GenericArrayTypeParser;
import com.jd.bingo.bean.mapper.builders.map.impl.ParameterizedTypeParser;
import com.jd.bingo.bean.mapper.builders.map.impl.TypeParser;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 13:55
 * @lastdate:
 */
@Deprecated
public class DefaultMapParser implements MapParser {

    private TypeParser<Class> classParser;
    private TypeParser<ParameterizedType> parameterizedTypeParser;
    private TypeParser<GenericArrayType> genericArrayTypeParser;
    private TypeParser<WildcardType> wildcardTypeTypeParser;

    public DefaultMapParser() {
        setClassParser(new ClassParser());
        setGenericArrayTypeParser(new GenericArrayTypeParser());
        setParameterizedTypeParser(new ParameterizedTypeParser());
    }

    public void setClassParser(TypeParser<Class> classParser) {
        this.classParser = classParser;
    }

    public void setParameterizedTypeParser(TypeParser<ParameterizedType> parameterizedTypeParser) {
        this.parameterizedTypeParser = parameterizedTypeParser;
    }

    public void setGenericArrayTypeParser(TypeParser<GenericArrayType> genericArrayTypeParser) {
        this.genericArrayTypeParser = genericArrayTypeParser;
    }

    public void setWildcardTypeTypeParser(TypeParser<WildcardType> wildcardTypeTypeParser) {
        this.wildcardTypeTypeParser = wildcardTypeTypeParser;
    }

    /**
     * 解析服务,type几种实现为ParameterizedType，GenericArrayType，WildcardType(出现时再度解析，所以到不了该步骤)，class，TypeVariable(出现时再度解析，所以到不了该步骤)
     *
     * @param source
     * @param target
     * @return
     */
    public MapUnit parse(Type source, Type target) {
        if (source instanceof Class) {
            return classParser.parse((Class) source, target);
        } else if (source instanceof ParameterizedType) {
            return parameterizedTypeParser.parse((ParameterizedType) source, target);
        } else if (source instanceof GenericArrayType) {
            return genericArrayTypeParser.parse((GenericArrayType) source, target);
        } else if (source instanceof WildcardType) {
            return wildcardTypeTypeParser.parse((WildcardType) source, target);
        }
        return null;
    }

}
