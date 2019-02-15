package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.BeanExpression;
import com.jd.bingo.bean.mapper.express.impl.DoNothingExpression;
import com.jd.bingo.bean.mapper.express.impl.ser.BeanMapGetSetter;
import com.jd.bingo.bean.mapper.express.impl.ser.GetSetter;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 17:15
 * @lastdate:
 */
public class BeanMapExpressionBuilder extends Builder<BeanMapMapUnit>{

    @Override
    protected Expression build(BeanMapMapUnit mu) {
        String name = createExpressionClassName(mu);
        Class<?> clazs = BeanUtil.createClass(name, BeanExpression.class,createMethods(mu));
        BeanExpression expression = (BeanExpression) BeanUtil.newInstance(clazs);
        Expression keyExpression = getExpression(mu.getKeyMU().getSouce(),mu.getKeyMU().getTarget());
        if(mu.getProperties() != null && !mu.getProperties().isEmpty()){
            for(MapUnit prop:mu.getProperties()){
                BeanMapGetSetter gs = createGetSetter(mu,prop);
                if(gs != null){
                    gs.setKeyExpression(keyExpression);
                    gs.setKeyStr(prop.getName());
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
    protected String[] createMethods(BeanMapMapUnit mu){
        String method = String.format(MethodTemplate.EXPRESSION_CREATE_TARGET,BeanUtil.getClassCastName(mu.getTarget()));
        return new String[]{method};
    }

    protected BeanMapGetSetter createGetSetter(BeanMapMapUnit bmu, MapUnit mu){
        Class<?> clazs = BeanUtil.createClass(BeanMapGetSetter.class.getName(),BeanMapGetSetter.class,createGetMethod(bmu,mu),createSetMethod(bmu,mu));
        return (BeanMapGetSetter) BeanUtil.newInstance(clazs);
    }

    protected String createGetMethod(BeanMapMapUnit bmu, MapUnit mu){
        return MethodTemplate.getSetterGetSourceMethod(bmu,mu);
    }

    protected String createSetMethod(BeanMapMapUnit bmu, MapUnit mu){
        StringBuilder builder = new StringBuilder("protected void set(Object desc, Object v){");
        builder.append("Object key = getKey();");
        builder.append("((Map)desc).put(key,v);");
        builder.append("}");
        return builder.toString();
    }
}
