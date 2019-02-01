package com.bingo.bean;

import com.bingo.bean.domain.Cat;
import com.bingo.bean.domain.Dog;
import com.bingo.bean.domain.FromBean;
import com.bingo.bean.domain.ParameterizedBean;
import com.bingo.bean.domain.ToBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.bingo.bean.mapper.ExpressionFactory;
import com.jd.bingo.bean.mapper.express.Expression;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 测评结果见：测评结果.txt
 * @author: libin29
 * @createdate: 2019/1/28 13:47
 * @lastdate:
 */
public class ExpressionTest {

    @Test
    public void doExpress(){
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
        FromBean fb = new FromBean();
        fb.setPet(cat);
        fb.setAddress("建华大街18号");
        fb.setAge(17);
        fb.setBirthday(new Date());
        fb.setHeight(188.97f);
        fb.setMoney(99999999999.999d);
        fb.setName("王二小");
        fb.setPb(new ParameterizedBean<>());
        fb.getPb().setData(cat);

        List<Coper> copers = new ArrayList<>();
        copers.add(new ExpressionCoper(fb));
        copers.add(new GetSetCoper(fb));
        copers.add(new FastJsonCoper(fb));
        copers.add(new DozerCoper(fb));
        copers.forEach(coper->coper.doCopy(1));
    }
}

abstract class Coper{
    protected FromBean fromBean;
    public Coper(FromBean fromBean){
        this.fromBean = fromBean;
        init(fromBean);
    }
    abstract void init(FromBean fromBean);
    public void doCopy(int times){
        System.out.println(getClass());
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            copy();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行" + times + "次，共耗时：" + (end-start)+"毫秒，平均每次耗时："+((end - start)*1f/times)+"毫秒");
    }
    abstract ToBean copy();
}

class GetSetCoper extends Coper{
    public GetSetCoper(FromBean fromBean) {
        super(fromBean);
    }

    @Override
    void init(FromBean fromBean) {

    }

    @Override
    ToBean copy() {
        ToBean toBean = new ToBean();
        toBean.setAddress(fromBean.getAddress());
        toBean.setAge(fromBean.getAge());
        toBean.setBirthday(fromBean.getBirthday().getTime());
        toBean.setHeight(Float.valueOf(fromBean.getHeight()).intValue());
        toBean.setMoney(Double.valueOf(fromBean.getMoney()).longValue());
        toBean.setName(fromBean.getName());
        Dog pet = new Dog();
        pet.setAge(fromBean.getPet().getAge());
        pet.setName(fromBean.getPet().getName());
        List<String> frends = new ArrayList<>();
        for (Integer i:fromBean.getPet().getFriends()){
            frends.add(i.toString());
        }
        pet.setFriends(frends);
        toBean.setPet(pet);
        return toBean;
    }
}

class ExpressionCoper extends Coper{

    Expression expression;
    public ExpressionCoper(FromBean fromBean) {
        super(fromBean);
    }

    @Override
    void init(FromBean fromBean) {
        ExpressionFactory factory = new ExpressionFactory();
        expression = factory.getExpression(FromBean.class,ToBean.class);
        System.out.println(expression.express(fromBean));
    }

    @Override
    ToBean copy() {
        return (ToBean) expression.express(fromBean);
    }
}

class FastJsonCoper extends Coper{
    ObjectMapper om;
    public FastJsonCoper(FromBean fromBean) {
        super(fromBean);
    }

    void init(FromBean fromBean) {
        om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(fromBean);
            ToBean toBean = om.readValue(json,ToBean.class);
            System.out.println(toBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ToBean copy() {
        try {
            String json = om.writeValueAsString(fromBean);
            ToBean toBean = om.readValue(json,ToBean.class);
            return toBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class DozerCoper extends Coper{
    DozerBeanMapper dozer;
    public DozerCoper(FromBean fromBean) {
        super(fromBean);
    }

    @Override
    void init(FromBean fromBean) {
        dozer = new DozerBeanMapper();
        ToBean toBean = dozer.map(fromBean,ToBean.class);
        System.out.println(toBean);
    }

    @Override
    ToBean copy() {
        return dozer.map(fromBean,ToBean.class);
    }
}
