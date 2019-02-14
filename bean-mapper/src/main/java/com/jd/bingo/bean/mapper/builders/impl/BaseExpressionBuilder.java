package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.AbstractExpression;
import com.jd.bingo.bean.mapper.express.impl.DoNothingExpression;
import com.jd.bingo.bean.mapper.express.impl.ToStringExpression;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 16:37
 * @lastdate:
 */
public class BaseExpressionBuilder extends Builder<MapUnit> {
    @Override
    protected Expression build(MapUnit mu) {
        Class source = BeanUtil.getWrapperClass((Class<?>) mu.getSouce());
        if(source == null){
            source = (Class) mu.getSouce();
        }
        Class target = BeanUtil.getWrapperClass((Class<?>) mu.getTarget());
        if(target == null){
            target = (Class) mu.getTarget();
        }
        if (source.equals(target)) {
            return DoNothingExpression.INSTANCE;
        }
        if (target.equals(String.class)) {
            return ToStringExpression.INSTANCE;
        } else {
            if (source.equals(String.class)) {
                if (BeanUtil.hasMethod(target,"valueOf",String.class)) {
                    Class clazs = BeanUtil.createClass(createExpressionClassName(mu),AbstractExpression.class,String.format(MethodTemplate.EXPRESSION_VALUEOF_STRING,BeanUtil.getClassCastName(target)));
                    return (Expression) BeanUtil.newInstance(clazs);
                }
            } else if (Number.class.isAssignableFrom(source) && Number.class.isAssignableFrom(target)) {
                String type = null;
                if(target.equals(Integer.class)){
                    type = "int";
                }else{
                    type = target.getSimpleName().toLowerCase();
                }
                Class clazs = BeanUtil.createClass(createExpressionClassName(mu),AbstractExpression.class,String.format(MethodTemplate.EXPRESSION_NUMBER_VALUE,BeanUtil.getClassCastName(target),type));
                return (Expression) BeanUtil.newInstance(clazs);
            }
        }
        return null;
    }
}
