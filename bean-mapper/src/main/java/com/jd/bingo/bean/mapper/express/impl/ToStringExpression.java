package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:24
 * @lastdate:
 */
public class ToStringExpression extends AbstractExpression {
    public static final ToStringExpression INSTANCE = new ToStringExpression();
    @Override
    protected Object doExpress(Object object) {
        return object.toString();
    }
}
