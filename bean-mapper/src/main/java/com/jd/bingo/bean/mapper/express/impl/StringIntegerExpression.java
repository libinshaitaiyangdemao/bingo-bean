package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:25
 * @lastdate:
 */
public class StringIntegerExpression extends AbstractExpression {
    public static final StringIntegerExpression INSTANCE = new StringIntegerExpression();
    @Override
    protected Object doExpress(Object object) {
        return Integer.valueOf((String) object);
    }
}
