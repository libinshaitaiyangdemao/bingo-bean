package com.bingo.bean.domain;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:38
 * @lastdate:
 */
public class ParameterizedFromBean {
    private ParameterizedBean<Cat> p;

    public ParameterizedBean<Cat> getP() {
        return p;
    }

    public void setP(ParameterizedBean<Cat> p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "ParameterizedFromBean{" +
                "p=" + p +
                '}';
    }
}
