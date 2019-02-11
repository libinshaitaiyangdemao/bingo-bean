package com.bingo.bean.domain;

import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/11 11:18
 * @lastdate:
 */
public class ParameterizedBean2<T,E> {
    private T data;
    private List<E> list;

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParameterizedBean2{" +
                "data=" + data +
                ", list=" + list +
                '}';
    }
}
