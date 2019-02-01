package com.jd.bingo.bean.mapper.annotation;

import com.jd.bingo.bean.mapper.express.Expression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 12:01
 * @lastdate:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Expressions {
    String value();
}
