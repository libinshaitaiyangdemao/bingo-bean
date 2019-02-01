package com.bingo.bean;

import com.jd.bingo.bean.mapper.ExpressionFactory;
import com.jd.bingo.bean.mapper.express.Expression;
import org.junit.Test;

import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/28 13:47
 * @lastdate:
 */
public class ExpressionTest {

    @Test
    public void doExpress(){
        Cat cat = new Cat();
        cat.setCatAge(3);
        cat.setCatName("TOM");
        Integer[] fs = new Integer[10];
        for (int i = 0; i < 10; i++) {
            fs[i] = i;
        }
        cat.setFriends(fs);
        ExpressionFactory factory = new ExpressionFactory();
        Expression expression = factory.getExpression(Cat.class,Dog.class);
        Dog dog = (Dog) expression.express(cat);
        System.out.println(dog);
    }
}
