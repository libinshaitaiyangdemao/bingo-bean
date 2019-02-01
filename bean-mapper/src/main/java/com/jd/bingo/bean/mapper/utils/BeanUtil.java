package com.jd.bingo.bean.mapper.utils;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/22 11:51
 * @lastdate:
 */
public class BeanUtil {

    public static <T> T newInstance(Class<T> clazs) {
        try {
            return clazs.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> createClass(String name, Class superClass, String... methods) {
        try {
            Class c = ClassAuthor.getInstance().create(name, superClass, methods);
            return c;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasMethod(Class clazs, String method, Class... args) {
        try {
            clazs.getDeclaredMethod(method, args);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static Method getGetMethod(Field field, Class clazs) {
        String name = field.getName();
        String methodName = null;
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            methodName = "is" + upFirstChar(name);
        } else {
            methodName = "get" + upFirstChar(name);
        }
        return getAccessibleMethod(clazs, methodName);
    }

    public static Method getSetMethod(Field field, Class clazs) {
        String name = field.getName();
        String methodName = "set" + upFirstChar(name);
        return getAccessibleMethod(clazs, methodName, field.getType());
    }

    private static String upFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public static List<Method> getMethodsByAnnotation(Class clazs, Class annotation) {
        List<Method> result = new ArrayList<>();
        for (Class<?> searchType = clazs; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getAnnotation(annotation) != null) {
                    result.add(method);
                }
            }
        }
        return result;
    }

    public static String getClassCastName(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getRawType().getTypeName();
        } else if (type instanceof Class) {
            if (((Class) type).isArray()) {
                return getClassCastName(((Class) type).getComponentType()) + "[]";
            } else {
                return ((Class) type).getName();
            }
        } else if (type instanceof GenericArrayType) {
            return getClassCastName(((GenericArrayType) type).getGenericComponentType()) + "[]";
        } else {
            return type.getTypeName();
        }
    }

    public static Class getWrapperClass(Class clazs) {
        if (!clazs.isPrimitive()) {
            return null;
        }
        if (clazs.equals(int.class)) {
            return Integer.class;
        } else if (clazs.equals(long.class)) {
            return Long.class;
        } else if (clazs.equals(double.class)) {
            return Double.class;
        } else if (clazs.equals(float.class)) {
            return Float.class;
        } else if (clazs.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazs.equals(short.class)) {
            return Short.class;
        } else if (clazs.equals(char.class)) {
            return Character.class;
        } else if (clazs.equals(byte.class)) {
            return Byte.class;
        }
        throw new RuntimeException("为找到对应包装类：" + clazs);
    }

    public static Field getField(final Class clazs, final String fieldName) {
        for (Class<?> superClass = clazs; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    public static List<Field> getFields(final Class clazs) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> superClass = clazs; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fs = superClass.getDeclaredFields();
                for (Field field : fs) {
                    fields.add(field);
                }
            } catch (Exception e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return fields;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj,
     * Object... args)
     */
    public static Method getAccessibleMethod(final Class clazs, final String methodName, final Class<?>... parameterTypes) {

        for (Class<?> searchType = clazs; searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
//                makeAccessible(method);
                if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                        && !method.isAccessible()) {
                    return null;
                }
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * 只匹配函数名。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj,
     * Object... args)
     */
    public static Method getAccessibleMethodByName(final Class clazs, final String methodName) {

        for (Class<?> searchType = clazs; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static List<Class> getExtendsBranch(Class son, Class father) {
        if (son.equals(father)) {
            List<Class> branch = new LinkedList<>();
            branch.add(son);
            return branch;
        }
        if (!father.isAssignableFrom(son)) {
            return null;
        }
        Class sf = son.getSuperclass();
        if (sf != null) {
            List<Class> find = getExtendsBranch(sf, father);
            if (find != null && !find.isEmpty()) {
                find.add(0, son);
                return find;
            }
        }
        //说明没找到
        if (father.isInterface()) {
            Class[] interfaces = son.getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                for (Class inf : interfaces) {
                    List<Class> find = getExtendsBranch(inf, father);
                    if (find != null && !find.isEmpty()) {
                        find.add(0, son);
                        return find;
                    }
                }
            }
        }
        return null;
    }

    public static Map<Class, List<DefiniteType>> getClassParameterizedActualTypeArguments(List<Class> branch) {
        if (branch == null || branch.isEmpty()) {
            return null;
        }
        Map<Class, List<DefiniteType>> map = new HashMap<>();
        Iterator<Class> iterator = branch.iterator();
        Class clazs = iterator.next();
        while (iterator.hasNext()) {
            Class next = iterator.next();
            if (next.isInterface()) {
                Type[] types = clazs.getGenericInterfaces();
                if (types == null || types.length < 1) {
                    clazs = next;
                } else {
                    for (Type t : types) {
                        if (t instanceof ParameterizedType && ((ParameterizedType) t).getRawType().equals(next)) {
                            List<DefiniteType> dt = getParameterizedActualTypeArguments(next, ((ParameterizedType) t).getActualTypeArguments());
                            if (dt != null) {
                                map.put(next, dt);
                            }
                            clazs = next;
                            break;
                        }
                    }
                }
            } else {
                Type father = clazs.getGenericSuperclass();
                if (father != null && father instanceof ParameterizedType) {
                    List<DefiniteType> dt = getParameterizedActualTypeArguments(next, ((ParameterizedType) father).getActualTypeArguments());
                    if (dt != null) {
                        map.put(next, dt);
                    }
                }
                clazs = next;
            }
        }
        if (!map.isEmpty()) {
            for (int i = 0; i < branch.size() - 1; i++) {
                Class c = branch.get(i);
                List<DefiniteType> dts = map.get(c);
                if (dts != null && !dts.isEmpty()) {
                    Class next = branch.get(i + 1);
                    List<DefiniteType> nextDts = map.get(next);
                    if (nextDts != null && !nextDts.isEmpty()) {
                        for (DefiniteType dt : nextDts) {
                            confirmDefiniteType(dt.getDefinited(), dts);
                            break;
                        }
                    }
                }
            }
        }
        return map;
    }

    public static Type confirmDefiniteType(Type dt, List<DefiniteType> dts) {
        if (dts == null || dts.isEmpty()) {
            return dt;
        }
        for (DefiniteType pDt : dts) {
            if (dt instanceof TypeVariable && ((TypeVariable) dt).getName().equals(pDt.getTypeVariable().getName())) {
                return pDt.getDefinited();
            }
        }
        return dt;
    }

    /**
     * 获取确认的泛型列表
     *
     * @param clazs
     * @param actualTypeArguments
     * @return
     */
    public static List<DefiniteType> getParameterizedActualTypeArguments(Class clazs, Type[] actualTypeArguments) {
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return null;
        }
        TypeVariable<Class<?>>[] tvs = clazs.getTypeParameters();
        if (tvs == null || tvs.length < 1) {
            return null;
        }
        if (tvs.length != actualTypeArguments.length) {
            throw new RuntimeException("泛型确认失败，数组长度不一样");
        }
        List<DefiniteType> list = new ArrayList<>();
        for (int i = 0; i < tvs.length; i++) {
            list.add(new DefiniteType(tvs[i], actualTypeArguments[i]));
        }
        return list;
    }

    /**
     * 确认该类继承关系上所有的泛型
     *
     * @param clazs
     * @param dts
     * @return
     */
    public static Map<Class, List<DefiniteType>> confirmSupperParameterizedActualTypeArguments(Class clazs, List<DefiniteType> dts) {
        Map<Class, List<DefiniteType>> map = new HashMap<>();
        //到该步骤时，已经不是枚举、数组
        if (!clazs.isInterface()) {
            Class supClass = clazs.getSuperclass();
            if (supClass != null && !supClass.equals(Object.class)) {
                Type type = clazs.getGenericSuperclass();
                if (type != null && type instanceof ParameterizedType) {
                    List<DefiniteType> supDts = getParameterizedActualTypeArguments((Class) ((ParameterizedType) type).getRawType(), ((ParameterizedType) type).getActualTypeArguments());
                    if (supDts != null && !supDts.isEmpty()) {
                        supDts.forEach(dt -> dt.setDefinited(confirmDefiniteType(dt.getDefinited(), dts)));
                        map.put((Class) ((ParameterizedType) type).getRawType(), supDts);
                        Map<Class, List<DefiniteType>> supMap = confirmSupperParameterizedActualTypeArguments((Class) ((ParameterizedType) type).getRawType(), supDts);
                        map.putAll(supMap);
                    }
                } else {
                    map.putAll(confirmSupperParameterizedActualTypeArguments(supClass, null));
                }
            }

        }
        Class[] interfaces = clazs.getInterfaces();
        List<Class> gencs = new ArrayList<>();
        if (interfaces != null && interfaces.length > 0) {
            Type[] types = clazs.getGenericInterfaces();
            if (types != null && types.length > 0) {
                for (Type t : types) {
                    if (t instanceof ParameterizedType) {
                        Class raw = (Class) ((ParameterizedType) t).getRawType();
                        List<DefiniteType> supDts = getParameterizedActualTypeArguments(raw, ((ParameterizedType) t).getActualTypeArguments());
                        if (supDts != null && !supDts.isEmpty()) {
                            supDts.forEach(dt -> dt.setDefinited(confirmDefiniteType(dt.getDefinited(), dts)));
                            map.put(raw, supDts);
                            Map<Class, List<DefiniteType>> supMap = confirmSupperParameterizedActualTypeArguments(raw, supDts);
                            map.putAll(supMap);
                        }
                        gencs.add(raw);
                    }
                }
            }
            for (Class inf : interfaces) {
                if (!gencs.contains(inf)) {
                    map.putAll(confirmSupperParameterizedActualTypeArguments(inf, null));
                }
            }
        }
        return map;
    }

    public static Type convertType(Type type) {
        if (type == null) {
            return Object.class;
        } else if (type instanceof TypeVariable) {
            Type[] types = ((TypeVariable) type).getBounds();
            if (types != null && types.length > 0) {
                for (Type t : types) {
                    if (t != null) {
                        return t;
                    }
                }
            }
        } else if (type instanceof WildcardType) {
            Type[] types = ((WildcardType) type).getLowerBounds();
            if (types != null && types.length > 0) {
                for (Type t : types) {
                    if (t != null) {
                        return t;
                    }
                }
            }
            types = ((WildcardType) type).getUpperBounds();
            if (types != null && types.length > 0) {
                for (Type t : types) {
                    if (t != null) {
                        return t;
                    }
                }
            }
        }
        return type;
    }

    /**
     * 获取继承关系上，所有的属性，并按照该属性被定义的类分类
     *
     * @param clazs
     * @param predicate
     * @return
     */
    public static Map<Class, List<Field>> getExtentsFields(Class clazs, Predicate<Field> predicate) {
        Map<Class, List<Field>> map = new HashMap<>();
        for (Class<?> superClass = clazs; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fs = superClass.getDeclaredFields();
                for (Field field : fs) {
                    if (predicate.test(field)) {
                        List<Field> fields = map.get(superClass);
                        if (fields == null) {
                            fields = new ArrayList<>();
                            map.put(superClass, fields);
                        }
                        fields.add(field);
                    }
                }
            } catch (Exception e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return map;
    }

    /**
     * 获取继承关系上所有是方法，并按照定义类分类
     *
     * @param clazs
     * @param predicate
     * @return
     */
    public static Map<Class, List<Method>> getExtentsMethods(Class clazs, Predicate<Method> predicate) {
        Map<Class, List<Method>> map = new HashMap<>();
        try {
            Method[] methods = clazs.getDeclaredMethods();
            for (Method m : methods) {
                if (predicate.test(m)) {
                    List<Method> ms = map.get(clazs);
                    if (ms == null) {
                        ms = new ArrayList<>();
                        map.put(clazs, ms);
                    }
                    ms.add(m);
                }
            }
        } catch (Exception e) {// NOSONAR
            // Field不在当前类定义,继续向上转型
        }
        Class superClass = clazs.getSuperclass();
        if(superClass != null && superClass != Object.class){
            map.putAll(getExtentsMethods(superClass,predicate));
        }
        Class[] interfaces = clazs.getInterfaces();
        if(interfaces != null && interfaces.length > 0){
            for(Class inf:interfaces){
                map.putAll(getExtentsMethods(inf,predicate));
            }
        }
        return map;
    }
}
