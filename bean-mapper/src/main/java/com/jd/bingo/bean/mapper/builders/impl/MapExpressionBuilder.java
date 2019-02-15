package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.map.entity.MAPMapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.BeanExpression;
import com.jd.bingo.bean.mapper.express.impl.ser.BeanMapGetSetter;
import com.jd.bingo.bean.mapper.express.impl.ser.MapGetSetter;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/15 10:03
 * @lastdate:
 */
public class MapExpressionBuilder extends Builder<MAPMapUnit> {

    @Override
    protected Expression build(MAPMapUnit mu) {
        String name = createExpressionClassName(mu);
        Class<?> clazs = BeanUtil.createClass(name, BeanExpression.class, createMethods(mu));
        BeanExpression expression = (BeanExpression) BeanUtil.newInstance(clazs);
        BeanMapGetSetter gs = createGetSetter(mu);
        if (gs != null) {
            expression.addGetSetter(gs);
        }
        return expression;
    }

    private BeanMapGetSetter createGetSetter(MAPMapUnit mu) {
        Expression keyExpression = getExpression(mu.getKeyMU().getSouce(), mu.getKeyMU().getTarget());
        Expression valueExpression = getExpression(mu.getValueMU().getSouce(), mu.getValueMU().getTarget());
        if(keyExpression == null || valueExpression == null){
            return null;
        }
        MapGetSetter gs = new MapGetSetter();
        gs.setKeyExpression(keyExpression);
        gs.setExpression(valueExpression);
        return gs;
    }

    protected String[] createMethods(MAPMapUnit mu) {
        String method = String.format(MethodTemplate.EXPRESSION_CREATE_TARGET, BeanUtil.getClassCastName(mu.getTarget()));
        return new String[]{method};
    }
}
