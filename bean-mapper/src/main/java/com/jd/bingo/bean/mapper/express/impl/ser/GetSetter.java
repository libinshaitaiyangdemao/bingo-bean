package com.jd.bingo.bean.mapper.express.impl.ser;

import com.jd.bingo.bean.mapper.express.Expression;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 17:42
 * @lastdate:
 */
public abstract class GetSetter {

    protected Expression expression;

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    private Object get(Object object) {
        Object v = getSource(object);
        return expression.express(v);
    }

    protected abstract void set(Object desc, Object v);

    protected abstract Object getSource(Object object);

    public void execute(Object source, Object desc) {
        Object v = get(source);
        set(desc, v);
    }
}
