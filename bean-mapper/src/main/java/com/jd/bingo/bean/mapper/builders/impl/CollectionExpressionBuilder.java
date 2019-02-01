package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.map.CollectionMapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.ArrayCollectionExpression;
import com.jd.bingo.bean.mapper.express.impl.ArrayExpression;
import com.jd.bingo.bean.mapper.express.impl.CollectionArrayExpression;
import com.jd.bingo.bean.mapper.express.impl.CollectionsExpression;
import com.jd.bingo.bean.mapper.express.impl.DoNothingExpression;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 17:45
 * @lastdate:
 */
public class CollectionExpressionBuilder extends Builder<CollectionMapUnit> {

    @Override
    protected Expression build(CollectionMapUnit mu) {
        if (mu.sameType() || (mu.getTarget().equals(mu.getSouce()) && mu.getElement().getTarget().equals(Object.class))) {
            return DoNothingExpression.INSTANCE;
        }
        if (mu.getElement().getSouce().equals(Object.class)) {
            throw new RuntimeException("无法对 Object.class 做映射");
        }
        Expression elementExpression = getExpression(mu.getElement().getSouce(), mu.getElement().getTarget());

        Class sc = null;
        String methods[] = new String[1];
        if ((mu.getTarget() instanceof Class && ((Class<?>) mu.getTarget()).isArray())||mu.getTarget() instanceof GenericArrayType) {
//            methods[0] = MethodTemplate.EXPRESSION_CREATE_NULL_TARGET;
            sc = ArrayExpression.class;
            if ((mu.getSouce() instanceof Class && ((Class<?>) mu.getSouce()).isArray())|| mu.getSouce() instanceof GenericArrayType) {
                methods[0] = MethodTemplate.arrayArrayExpressionCopyMethod(mu.getSouce(),mu.getTarget());
            } else {
                methods[0] = MethodTemplate.collectionArrayExpressionCopyMethod(mu.getTarget());
            }

        } else {
             methods[0] = String.format(MethodTemplate.EXPRESSION_CREATE_TARGET, getCollectionInstanceClass((Class<? extends Collection>) ((ParameterizedType)mu.getTarget()).getRawType()).getName());
            if ((mu.getSouce() instanceof Class && ((Class<?>) mu.getSouce()).isArray())|| mu.getSouce() instanceof GenericArrayType) {
                sc = ArrayCollectionExpression.class;
            } else {
                sc = CollectionsExpression.class;
            }
        }
        Class clazs = BeanUtil.createClass(createExpressionClassName(mu) + "_" + BeanUtil.getClassCastName(mu.getElement().getSouce()) + "_" + BeanUtil.getClassCastName(mu.getElement().getTarget()), sc, methods);
        CollectionsExpression result = (CollectionsExpression) BeanUtil.newInstance(clazs);
        result.setExpression(elementExpression);
        return result;
    }

    protected Class getCollectionInstanceClass(Class<? extends Collection> clazs){
        if(clazs.isAssignableFrom(ArrayList.class)){
            return ArrayList.class;
        }else if(clazs.isAssignableFrom(HashSet.class)){
            return HashSet.class;
        }else if(clazs.isAssignableFrom(BlockingDeque.class)){
            return ArrayBlockingQueue.class;
        }else if(clazs.isAssignableFrom(LinkedHashSet.class)){
            return LinkedHashSet.class;
        }else if(clazs.isAssignableFrom(LinkedList.class)){
            return LinkedList.class;
        }else{
            return ArrayList.class;
        }
    }
}
