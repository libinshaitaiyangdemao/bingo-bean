package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 18:13
 * @lastdate:
 */
public class MapBeanExpressionBuilder extends BeanMapExpressionBuilder{

    @Override
    protected String createGetMethod(BeanMapMapUnit bmu, MapUnit mu) {
        StringBuilder builder = new StringBuilder("protected Object getSource(Object object){");
        builder.append("return ((Map)object).get(getKey());}");
        return builder.toString();
    }

    @Override
    protected String createSetMethod(BeanMapMapUnit bmu, MapUnit mu) {
        return MethodTemplate.getSetterGetSetMethod(bmu,mu);
    }
}
