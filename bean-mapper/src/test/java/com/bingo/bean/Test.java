package com.bingo.bean;

import com.jd.bingo.bean.mapper.builders.impl.MethodTemplate;
import com.jd.bingo.bean.mapper.utils.BeanUtil;
import com.jd.bingo.bean.mapper.utils.DefiniteType;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/24 13:36
 * @lastdate:
 */
public class Test {
    @org.junit.Test
    public void test1() throws Exception{
//        Type atype = A.class.getDeclaredField("nn").getGenericType();
//        Type btype = B.class.getDeclaredField("c").getGenericType();
//        System.out.println(atype.getClass());
//        System.out.println(btype.getClass());
//        System.out.println(btype.equals(atype));
//
//        System.out.println(Objects.equals('c',"c"));
//
//        Type t = A.class.getDeclaredField("list").getGenericType();
//        System.out.println(t.getClass());
//        Integer[][][][] o = new Integer[2][][][];
//        o[0] = null;
//        System.out.println(BeanUtil.getClassCastName(java.lang.Integer[].class));
//        System.out.println(((GenericArrayType)t).getGenericComponentType().getClass());
//        System.out.println(MethodTemplate.collectionArrayExpressionCopyMethod(t));
//        TypeVariable ct1 = (TypeVariable) BeanUtil.getField(D.class,"age").getGenericType();
//        TypeVariable ct2 = (TypeVariable) BeanUtil.getField(D.class,"name").getGenericType();
//        ct1.getGenericDeclaration().getTypeParameters();
//        ParameterizedType et1 = (ParameterizedType) D.class.getGenericSuperclass();
//        Type[] types = ct1.getBounds();
//        System.out.println(C.class.getDeclaredField("name").getGenericType().getClass());
//        System.out.println(A.class.getDeclaredField("bUpList").getGenericType().getClass());
//        System.out.println(A.class.getDeclaredField("bLowList").getGenericType().getClass());
//        System.out.println(A.class.getDeclaredField("generalArray").getType());
//        Type[] types = ListB.class.getGenericInterfaces();
//        System.out.println(types);
//        List<Class> extendsBranch = BeanUtil.getExtendsBranch(ListB.class,Collection.class);
//        extendsBranch.forEach(System.out::println);
//        TypeVariable<Class<ListC>>[] tvs = ListC.class.getTypeParameters();
//        Type t = ListC.class.getGenericSuperclass();
//        System.out.println(tvs.length);
//        List<Class> branch = BeanUtil.getExtendsBranch(ListC.class,Collection.class);
//        Map<Class,List<DefiniteType>> map = BeanUtil.getClassParameterizedActualTypeArguments(branch);
//        if(map != null){
//            List<DefiniteType> dt = map.get(Collection.class);
//            System.out.println(dt);
//        }
        Map<Class,List<DefiniteType>> map = BeanUtil.confirmSupperParameterizedActualTypeArguments(BSup.class,null);
        System.out.println(map);
    }

    @org.junit.Test
    public void mapTest(){
        Key k = new Key();
        k.name = "1";
        Map<Key,String> map = new HashMap<>();
        map.put(k,"1");
        k.name = "2";
        System.out.println(map.get(k));
        k.name = "1";
        System.out.println(map.get(k));
    }

    @org.junit.Test
    public void arrayTest(){
        String[] strs = new String[100000];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = i + "";
        }

        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String[] copy = new String[strs.length];
            for (int j = 0; j < strs.length; j++) {
                copy[j] = strs[j];
//                Array.set(copy,j,strs[i]);
            }
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}

class A{
    List list;
    String nn;
    String[][] array;
    List<String>[][] generalArray;
    List<? extends B> bUpList;
    List<? super B> bLowList;
}
class B{
    List<String> list;
    Integer nn;
    Integer[] array;
    C<? extends Date,?> c;
}

class C<T,E> {
    T age;
    E name;
}
class D<E> extends C<Integer,String>{
    E bd;
}
class ListA extends ArrayList<String>{

}
abstract class BSup extends ListB<String,Integer,Long>{

}
abstract class ListB<U,T,F> extends C<T,F> implements List<U>{}
abstract class ListC extends ListB<String,Integer,Long>{}
class E extends D<String>{}
class Key{
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        return name != null ? name.equals(key.name) : key.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}