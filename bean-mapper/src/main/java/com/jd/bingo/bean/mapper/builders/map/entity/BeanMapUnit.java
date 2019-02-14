package com.jd.bingo.bean.mapper.builders.map.entity;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 14:58
 * @lastdate:
 */
public class BeanMapUnit extends MapUnit {

    private List<MapUnit> properties;

    public List<MapUnit> getProperties() {
        return properties;
    }

    public void setProperties(List<MapUnit> properties) {
        this.properties = properties;
    }
}
