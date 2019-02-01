package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:25
 * @lastdate:
 */
public class StringFloatExpression extends AbstractExpression {
    public static final StringFloatExpression INSTANCE = new StringFloatExpression();
    @Override
    protected Object doExpress(Object object) {
        return Float.valueOf((String) object);
    }
}
