package com.jd.bingo.bean.mapper.builders.map.impl;

import com.jd.bingo.bean.mapper.builders.map.MapParser;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/30 18:05
 * @lastdate:
 */
public class ChainMapParser implements MapParser {

    public ChainMapParser() {
        addNode(new BaseMapUnitParseNoder());
        addNode(new CollectionMapUnitParseNoder());
        addNode(new BeanMapMapUnitParseNoder());
        addNode(new MapBeanMapUnitParseNoder());
        addNode(new MAPMapUnitParseNoder());
        addNode(new BeanMapUnitParseNoder());
    }

    /**
     * 处理节点
     */
    private List<ChainMapParseNoder> noders;
    public void addNode(ChainMapParseNoder noder){
        if(noders == null){
            noders = new ArrayList<>();
        }
        noders.add(noder);
    }
    /**
     * 解析服务,type几种实现为ParameterizedType，GenericArrayType，WildcardType(出现时再度解析，所以到不了该步骤)，class，TypeVariable(出现时再度解析，所以到不了该步骤)
     *
     * @param source
     * @param target
     * @return
     */
    @Override
    public MapUnit parse(Type source, Type target) {
        for(ChainMapParseNoder noder : noders){
            MapUnit mu = noder.parse(source,target);
            if(mu != null){
                return mu;
            }
        }
        return null;
    }
}
