package com.jd.bingo.bean.mapper.express.impl.ser;

import com.jd.bingo.bean.mapper.express.Expression;

import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 17:47
 * @lastdate:
 */
public abstract class BeanMapGetSetter extends GetSetter {

    private String keyStr;
    protected Expression keyExpression;

    public void setKeyExpression(Expression keyExpression) {
        this.keyExpression = keyExpression;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    protected Object getKey(){
        return keyExpression.express(keyStr);
    }
}
