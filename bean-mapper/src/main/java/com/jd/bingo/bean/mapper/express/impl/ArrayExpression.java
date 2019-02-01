package com.jd.bingo.bean.mapper.express.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 10:53
 * @lastdate:
 */
public class ArrayExpression extends CollectionsExpression {

    /**
     * 创建目标文件
     *
     * @return
     */
    @Override
    protected Object createTarget() {
        return null;
    }

        /**
     * 复制属性
     *
     * @param source
     * @param target
     */
    @Override
    protected Object copy(Object source, Object target) {
//        int length = Array.getLength(source);
//        target = createArray(length);
//        for (int i = 0; i < length; i++) {
//            Object t = expression.express(Array.get(source,i));
////            c.add(t);
//        }
//
//        return target;
        return null;
    }

}
