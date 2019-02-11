package com.bingo.bean.domain;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:38
 * @lastdate:
 */
public class ParameterizedToBean {

    private ParameterizedBean<Dog,Integer> p;

    public ParameterizedBean<Dog,Integer> getP() {
        return p;
    }

    public void setP(ParameterizedBean<Dog,Integer> p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "ParameterizedFromBean{" +
                "p=" + p +
                '}';
    }
}
