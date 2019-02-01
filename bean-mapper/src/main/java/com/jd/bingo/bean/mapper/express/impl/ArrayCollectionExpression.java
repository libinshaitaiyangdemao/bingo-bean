package com.jd.bingo.bean.mapper.express.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 11:11
 * @lastdate:
 */
public abstract class ArrayCollectionExpression extends CollectionsExpression {

    /**
     * 复制属性
     *
     * @param source
     * @param target
     */
    @Override
    protected Object copy(Object source, Object target) {
        int length = Array.getLength(source);
        Collection cTarget = (Collection) target;
        for (int i = 0; i < length; i++) {
            Object t = expression.express(Array.get(source,i));
//            t = super.copy(Array.get(source,i),t);
            cTarget.add(t);
        }
        return target;
    }
}
