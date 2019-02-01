package com.jd.bingo.bean.mapper.express.impl;

import com.jd.bingo.bean.mapper.express.impl.ser.GetSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/21 16:00
 * @lastdate:
 */
public abstract class BeanExpression extends AbstractExpression {

    protected List<GetSetter> getSetters;

    public BeanExpression(){
        getSetters = new ArrayList<>();
    }

    public void addGetSetter(GetSetter gs){
        getSetters.add(gs);
    }
    @Override
    protected Object doExpress(Object object) {
        Object target = createTarget();
        target = copy(object,target);
        return target;
    }

    /**
     * 创建目标文件
     *
     * @return
     */
    protected abstract Object createTarget();

    /**
     * 复制属性
     *
     * @param source
     * @param target
     */
    protected Object copy(Object source,Object target){
        getSetters.forEach(gs->gs.execute(source,target));
        return target;
    }
}
