package com.bingo.bean;

import com.bingo.bean.domain.Cat;
import com.bingo.bean.domain.ParameterizedBean;
import com.bingo.bean.domain.ParameterizedBean2;
import com.bingo.bean.domain.ParameterizedFromBean;
import com.bingo.bean.domain.ParameterizedToBean;
import com.jd.bingo.bean.mapper.ExpressionFactory;
import com.jd.bingo.bean.mapper.express.Expression;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/2/1 16:05
 * @lastdate:
 */
public class DeepParameterizedTest {

    @Test
    public void test1(){
        Cat cat = new Cat();
//        cat.setCatAge(3);
        cat.setAge(3);
//        cat.setCatName("TOM");
        cat.setName("TOM");
        Integer[] fs = new Integer[10];
        for (int i = 0; i < 10; i++) {
            fs[i] = i;
        }
        cat.setFriends(fs);
        ParameterizedBean<Cat,String> p = new ParameterizedBean<>();
        p.setData(cat);
        p.setList(new ArrayList<>());
        p.getList().add("2");
        ParameterizedFromBean from = new ParameterizedFromBean();
        from.setP(p);
        p.setPb(new ParameterizedBean2<>());
        p.getPb().setData(cat);
        p.getPb().setList(p.getList());
        ExpressionFactory factory = new ExpressionFactory();
        Expression expression = factory.getExpression(ParameterizedFromBean.class, ParameterizedToBean.class);
        System.out.println(expression.express(from));
    }
}
