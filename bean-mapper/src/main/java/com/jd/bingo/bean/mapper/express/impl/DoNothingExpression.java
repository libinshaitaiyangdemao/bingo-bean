package com.jd.bingo.bean.mapper.express.impl;

import com.jd.bingo.bean.mapper.express.Expression;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 15:17
 * @lastdate:
 */
public class DoNothingExpression implements Expression {
    public static final DoNothingExpression INSTANCE = new DoNothingExpression();
    public Object express(Object object) {
        return object;
    }
}
