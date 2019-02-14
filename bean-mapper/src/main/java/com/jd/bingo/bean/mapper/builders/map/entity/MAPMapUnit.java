package com.jd.bingo.bean.mapper.builders.map.entity;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/14 15:01
 * @lastdate:
 */
public class MAPMapUnit extends MapUnit{

    private MapUnit keyMU;

    private MapUnit valueMU;

    public MapUnit getKeyMU() {
        return keyMU;
    }

    public void setKeyMU(MapUnit keyMU) {
        this.keyMU = keyMU;
    }

    public MapUnit getValueMU() {
        return valueMU;
    }

    public void setValueMU(MapUnit valueMU) {
        this.valueMU = valueMU;
    }
}
