package com.jd.bingo.bean.mapper.express.impl;

import com.jd.bingo.bean.mapper.express.Expression;

import java.util.Collection;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 10:46
 * @lastdate:
 */
public abstract class CollectionsExpression extends BeanExpression {

    protected Expression expression;

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * 复制属性
     *
     * @param source
     * @param target
     */
    @Override
    protected Object copy(Object source, Object target) {
        Collection cSource = (Collection) source;
        Collection cTarget = (Collection) target;
        cSource.forEach(o -> {
            Object t = expression.express(o);
//            super.copy(o,t);
            cTarget.add(t);
        });
        return target;
    }

}
