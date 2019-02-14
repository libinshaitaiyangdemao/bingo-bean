package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.BeanExpression;
import com.jd.bingo.bean.mapper.express.impl.DoNothingExpression;
import com.jd.bingo.bean.mapper.express.impl.ser.GetSetter;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

/**
 * @description:对每个属性，使用GETSETER进行拷贝
 *
 * @author: libin29
 * @createdate: 2019/1/23 18:18
 * @lastdate:
 */
public class BeanExpressionBuilder extends Builder<BeanMapUnit>{

    @Override
    protected Expression build(BeanMapUnit mu) {
        if(mu.sameType()||mu.getSouce() instanceof Class && mu.getTarget() instanceof Class && ((Class) mu.getTarget()).isAssignableFrom((Class<?>) mu.getSouce())){
            return DoNothingExpression.INSTANCE;
        }
        String name = createExpressionClassName(mu);
        Class<?> clazs = BeanUtil.createClass(name, BeanExpression.class,createMethods(mu));
        BeanExpression expression = (BeanExpression) BeanUtil.newInstance(clazs);
        if(mu.getProperties() != null && !mu.getProperties().isEmpty()){
            for(MapUnit prop:mu.getProperties()){
                GetSetter gs = createGetSetter(mu,prop);
                if(gs != null){
                    try {
                        gs.setExpression(getExpression(prop.getSouce(),prop.getTarget()));
                        expression.addGetSetter(gs);
                    } catch (Exception e) {
                        //属性可能不可映射，跳过
                        e.printStackTrace();
                    }
                }
            }
        }
        return expression;
    }
    protected String[] createMethods(BeanMapUnit mu){
        String method = String.format(MethodTemplate.EXPRESSION_CREATE_TARGET,BeanUtil.getClassCastName(mu.getTarget()));
        return new String[]{method};
    }

    protected GetSetter createGetSetter(BeanMapUnit bmu,MapUnit mu){
        Class<?> clazs = BeanUtil.createClass(GetSetter.class.getName(),GetSetter.class,MethodTemplate.getSetterGetSourceMethod(bmu,mu),MethodTemplate.getSetterGetSetMethod(bmu,mu));

        return (GetSetter) BeanUtil.newInstance(clazs);
    }
}
