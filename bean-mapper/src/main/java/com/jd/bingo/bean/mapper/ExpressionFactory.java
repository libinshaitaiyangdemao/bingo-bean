package com.jd.bingo.bean.mapper;

import com.jd.bingo.bean.mapper.builders.Builder;
import com.jd.bingo.bean.mapper.builders.ExpressionBuildHandler;
import com.jd.bingo.bean.mapper.builders.map.impl.ChainMapParser;
import com.jd.bingo.bean.mapper.builders.map.impl.DefaultMapParser;
import com.jd.bingo.bean.mapper.builders.map.MapParser;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.express.Expression;
import com.jd.bingo.bean.mapper.utils.ConcurrentLocalStore;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 16:06
 * @lastdate:
 */
public class ExpressionFactory {
    protected static class Key{
        Type source;
        Type target;

        public Key(Type source, Type target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (!source.equals(key.source)) return false;
            return target.equals(key.target);
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }
    }
    private ExpressionBuildHandler expressionBuildHandler;
    private ConcurrentLocalStore<Key,Expression> store;
    public ExpressionFactory() {
        this(null);
    }
    public ExpressionFactory(MapParser parser) {
        if(parser == null){
            parser = createMapParser();
        }
        expressionBuildHandler = createExpressionBuildHandler(parser);
        store = new ConcurrentLocalStore<>(key -> expressionBuildHandler.build(key.source,key.target));
    }

    protected ExpressionBuildHandler createExpressionBuildHandler(MapParser parser){
        return new ExpressionBuildHandler(this,parser);
    }
    protected MapParser createMapParser(){
//        return new DefaultMapParser();
        return new ChainMapParser();
    }

    public void setBuilder(Class<? extends MapUnit> clazs, Builder<? extends MapUnit> builder){
        expressionBuildHandler.setBuilder(clazs,builder);
    }

    public Expression getExpression(Type source, Type target){
        return store.get(new Key(source,target));
    }
}
