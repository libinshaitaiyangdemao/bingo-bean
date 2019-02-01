package com.bingo.bean.domain;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:06
 * @lastdate:
 */
public class ParameterizedBean<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParameterizedBean{" +
                "data=" + data +
                '}';
    }
}
