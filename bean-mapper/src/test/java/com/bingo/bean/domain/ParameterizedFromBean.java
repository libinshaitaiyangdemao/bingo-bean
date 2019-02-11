package com.bingo.bean.domain;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:38
 * @lastdate:
 */
public class ParameterizedFromBean {
    private ParameterizedBean<Cat,String> p;

    public ParameterizedBean<Cat,String> getP() {
        return p;
    }

    public void setP(ParameterizedBean<Cat,String> p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "ParameterizedFromBean{" +
                "p=" + p +
                '}';
    }
}
