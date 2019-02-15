package com.jd.bingo.bean.mapper.express.impl.ser;

import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/15 10:10
 * @lastdate:
 */
public class MapGetSetter extends BeanMapGetSetter {

    @Override
    protected void set(Object desc, Object v) {

    }

    @Override
    protected Object getSource(Object object) {
        return null;
    }

    @Override
    public void execute(Object source, Object desc) {
        Map sMap = Map.class.cast(source);
        Map tMap = Map.class.cast(desc);
        Iterator iterator = sMap.keySet().iterator();
        while (iterator.hasNext()){
            Object k = iterator.next();
            Object v = sMap.get(k);
            tMap.put(keyExpression.express(k),expression.express(v));
        }
    }
}
