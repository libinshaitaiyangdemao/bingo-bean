package com.jd.bingo.bean.mapper.builders.map;

import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 15:00
 * @lastdate:
 */
public class CollectionMapUnit extends MapUnit {

    private MapUnit element;

    public MapUnit getElement() {
        return element;
    }

    public void setElement(MapUnit element) {
        this.element = element;
    }

    @Override
    public boolean sameType() {
        return super.sameType()&& element.sameType();
    }
}
