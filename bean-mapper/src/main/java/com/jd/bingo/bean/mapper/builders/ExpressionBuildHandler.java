package com.jd.bingo.bean.mapper.builders;

import com.jd.bingo.bean.mapper.ExpressionFactory;
import com.jd.bingo.bean.mapper.builders.impl.BaseExpressionBuilder;
import com.jd.bingo.bean.mapper.builders.impl.BeanExpressionBuilder;
import com.jd.bingo.bean.mapper.builders.impl.CollectionExpressionBuilder;
import com.jd.bingo.bean.mapper.builders.map.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.CollectionMapUnit;
import com.jd.bingo.bean.mapper.builders.map.MapParser;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 表达式创建者
 * @author: libin29
 * @createdate: 2019/1/22 11:55
 * @lastdate:
 */
public class ExpressionBuildHandler {

    private ExpressionFactory expressionFactory;

    protected Map<Class<? extends MapUnit>, Builder<? extends MapUnit>> builders;

    private MapParser parser;

    public ExpressionBuildHandler(ExpressionFactory expressionFactory,MapParser parser) {
        this.builders = new ConcurrentHashMap<>();
        this.parser = parser;
        this.expressionFactory = expressionFactory;
        init();
    }

    public ExpressionFactory getExpressionFactory() {
        return expressionFactory;
    }

    protected void init(){
        setBuilder(MapUnit.class,new BaseExpressionBuilder());
        setBuilder(CollectionMapUnit.class,new CollectionExpressionBuilder());
        setBuilder(BeanMapUnit.class,new BeanExpressionBuilder());
    }
    public void setBuilder(Class<? extends MapUnit> clazs,Builder<? extends MapUnit> builder){
        builders.put(clazs,builder);
        builder.setExpressionBuilder(this);
    }

    public Expression build(Type source, Type target) {
        MapUnit mu = parser.parse(source, target);
        if (mu == null) {
            throw new RuntimeException("解析映射关系错误");
        }
        Builder builder = builders.get(mu.getClass());
        if (builder == null) {
            throw new RuntimeException("不支持的 MapUnit 类型：" + mu.getClass());
        }
        Expression expression = builder.create(mu);
        if(expression == null){
            throw new RuntimeException("无法映射");
        }
        return expression;
    }
}
