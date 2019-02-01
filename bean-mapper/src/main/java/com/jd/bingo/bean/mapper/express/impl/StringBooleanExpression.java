package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:25
 * @lastdate:
 */
public class StringBooleanExpression extends AbstractExpression {
    public static final StringBooleanExpression INSTANCE = new StringBooleanExpression();
    @Override
    protected Object doExpress(Object object) {
        return Boolean.valueOf((String) object);
    }
}
