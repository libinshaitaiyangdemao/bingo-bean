package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:25
 * @lastdate:
 */
public class StringDoubleExpression extends AbstractExpression {
    public static final StringDoubleExpression INSTANCE = new StringDoubleExpression();
    @Override
    protected Object doExpress(Object object) {
        return Double.valueOf((String) object);
    }
}
