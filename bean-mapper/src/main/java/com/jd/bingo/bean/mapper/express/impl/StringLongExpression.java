package com.jd.bingo.bean.mapper.express.impl;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:25
 * @lastdate:
 */
public class StringLongExpression extends AbstractExpression {
    public static final StringLongExpression INSTANCE = new StringLongExpression();
    @Override
    protected Object doExpress(Object object) {
        return Long.valueOf((String) object);
    }
}
