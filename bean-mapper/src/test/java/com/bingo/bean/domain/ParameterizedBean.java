package com.bingo.bean.domain;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:06
 * @lastdate:
 */
public class ParameterizedBean<T,E> {

    private T data;
    private List<E> list;
    private ParameterizedBean2<T,E> pb;

    public ParameterizedBean2<T, E> getPb() {
        return pb;
    }

    public void setPb(ParameterizedBean2<T, E> pb) {
        this.pb = pb;
    }

    public T getData() {
        return data;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParameterizedBean{" +
                "data=" + data +
                ", list=" + list +
                ", pb=" + pb +
                '}';
    }
}
