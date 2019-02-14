package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.map.entity.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.express.impl.ser.GetSetter;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:对于相同的属性，直接调用方法赋值
 *
 * @author: libin29
 * @createdate: 2019/2/11 17:08
 * @lastdate:
 */
public class BeanFastExpressionBuilder extends BeanExpressionBuilder {

    @Override
    protected String[] createMethods(BeanMapUnit mu) {
        String[] result =  super.createMethods(mu);
        if(mu.getProperties() != null && !mu.getProperties().isEmpty()){
            List<MapUnit> pmus = mu.getProperties().stream().filter(MapUnit::sameType).collect(Collectors.toList());
            if(pmus != null && !pmus.isEmpty()){
                StringBuilder builder = new StringBuilder("protected Object copy(Object source,Object target){target = super.copy(source,target);");
                String source = BeanUtil.getClassCastName(mu.getSouce());
                String target = BeanUtil.getClassCastName(mu.getTarget());
                mu.getProperties().forEach(pmu->{
                    if(pmu.sameType()){
                        builder.append("((").append(target).append(")target).").append(pmu.getSetMethodName()).append("(((").append(source).append(")source).").append(pmu.getGetMethodName()).append("());");
                    }
                });
                builder.append("return target;}");

                String[] methods = new String[result.length + 1];
                System.arraycopy(result,0,methods,0,result.length);
                methods[result.length] = builder.toString();
                return methods;
            }
        }
        return result;
    }

    @Override
    protected GetSetter createGetSetter(BeanMapUnit bmu, MapUnit mu) {
        if(!mu.sameType()){
            return super.createGetSetter(bmu, mu);
        }
        return null;
    }
}
