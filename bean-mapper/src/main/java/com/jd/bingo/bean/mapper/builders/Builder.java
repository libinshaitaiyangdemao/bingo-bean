package com.jd.bingo.bean.mapper.builders;

import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.express.impl.DoNothingExpression;

import java.lang.reflect.Type;

public abstract class Builder<T extends MapUnit> {

    private ExpressionBuildHandler expressionBuilder;

    public ExpressionBuildHandler getExpressionBuilder() {
        return expressionBuilder;
    }

    public void setExpressionBuilder(ExpressionBuildHandler expressionBuilder) {
        this.expressionBuilder = expressionBuilder;
    }

    public Expression create(T mu) {
        return build(mu);
    }

    protected abstract Expression build(T mu);

    protected String createExpressionClassName(MapUnit mu){
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append("$$").append(mu.getSouce().toString()).append("_").append(mu.getTarget().toString()).append("_Expression");
        return sb.toString();
    }

    protected Expression getExpression(Type source,Type target){
        if(source.equals(target)){
            return DoNothingExpression.INSTANCE;
        }
        //多线程情况下，嵌套引用可能产生死锁
//        return getExpressionBuilder().getExpressionFactory().getExpression(source,target);
        //避免多线程情况下，嵌套引用产生死锁的可能性
        return getExpressionBuilder().build(source,target);
    }
}