package com.jd.bingo.bean.mapper.express.impl;

import com.jd.bingo.bean.mapper.express.Expression;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:19
 * @lastdate:
 */
public abstract class AbstractExpression implements Expression{
    public Object express(Object object) {
        if (object == null) {
            return null;
        }
        return doExpress(object);
    }

    protected abstract Object doExpress(Object object);
}
