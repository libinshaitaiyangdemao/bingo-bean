package com.bingo.bean.map;

import com.bingo.bean.domain.Cat;
import com.bingo.bean.domain.Dog;
import com.jd.bingo.bean.mapper.builders.map.entity.MapUnit;
import com.jd.bingo.bean.mapper.builders.map.impl.ParseUtil;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/28 11:26
 * @lastdate:
 */
public class ParseTest {

    @Test
    public void mapProperties(){
        List<MapUnit> mus = ParseUtil.mapProperties(Cat.class, Dog.class);
        System.out.println(mus.size());
    }
}
