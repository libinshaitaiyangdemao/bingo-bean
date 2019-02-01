package com.jd.bingo.bean.mapper.builders.impl;

import com.jd.bingo.bean.mapper.builders.map.BeanMapUnit;
import com.jd.bingo.bean.mapper.builders.map.MapUnit;
import com.jd.bingo.bean.mapper.utils.BeanUtil;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/23 17:21
 * @lastdate:
 */
public interface MethodTemplate {

    String EXPRESSION_VALUEOF_STRING = "public Object doExpress(Object object){return %s.valueOf((String)object);}";
    String EXPRESSION_NUMBER_VALUE = "public Object doExpress(Object object){return %s.valueOf(((Number)object).%sValue());}";
    String EXPRESSION_CREATE_TARGET = "protected Object createTarget(){return new %s();}";
    String EXPRESSION_CREATE_NULL_TARGET = "protected Object createTarget(){return null;}";
    String GETSETTER_SET = "protected void set(Object desc, Object v){((%s)desc).%s((%s)v);}";
    String GETSETTER_GETSOURCE = "protected Object getSource(Object object){return ((%s)object).%s();}";

    static String getSetterGetSourceMethod(BeanMapUnit bmu, MapUnit pmu) {
        StringBuilder builder = new StringBuilder("protected Object getSource(Object object){");
        if (pmu.getSouce() instanceof Class && ((Class) pmu.getSouce()).isPrimitive()) {
            builder.append(" ").append(BeanUtil.getClassCastName(pmu.getSouce())).append(" value = ").append("((").append(BeanUtil.getClassCastName(bmu.getSouce())).append(")object).").append(pmu.getGetMethodName()).append("();");
            Class wrapperClass = BeanUtil.getWrapperClass((Class) pmu.getSouce());
            builder.append("return ").append(wrapperClass.getName()).append(".valueOf(value);");
        } else {
            builder.append("return ").append("((").append(BeanUtil.getClassCastName(bmu.getSouce())).append(")object).").append(pmu.getGetMethodName()).append("();");
        }
        builder.append("}");
        return builder.toString();
    }

    static String getSetterGetSetMethod(BeanMapUnit bmu, MapUnit pmu) {
        StringBuilder builder = new StringBuilder("protected void set(Object desc, Object v){((").append(BeanUtil.getClassCastName(bmu.getTarget())).append(")desc).").append(pmu.getSetMethodName());
        if (pmu.getTarget() instanceof Class && ((Class) pmu.getTarget()).isPrimitive()) {
            Class wrapperClass = BeanUtil.getWrapperClass((Class) pmu.getTarget());
            builder.append("(((").append(wrapperClass.getName()).append(")v).").append(BeanUtil.getClassCastName(pmu.getTarget())).append("Value());}");
        } else {
            builder.append("((").append(BeanUtil.getClassCastName(pmu.getTarget())).append(")v);}");
        }return builder.toString();
    }

    static String collectionArrayExpressionCopyMethod(Type type) {
        String classCastName = BeanUtil.getClassCastName(type);
        String lastComponent = classCastName.substring(0, classCastName.indexOf("["));
        String wd = classCastName.substring(lastComponent.length());
        if (wd.startsWith("[]")) {
            wd = wd.substring(2);
        }
        StringBuilder builder = new StringBuilder("protected Object copy(Object source, Object target) {");
        builder.append("java.util.Collection cSource = (java.util.Collection) source;");
        builder.append(classCastName).append(" t = new ").append(lastComponent).append("[cSource.size()]").append(wd).append(";");
        builder.append("int i = 0;");
        builder.append("java.util.Iterator iterator = cSource.iterator();");
        builder.append("while (iterator.hasNext()){");
        builder.append("t[i] = (").append(lastComponent).append(wd).append(")expression.express(iterator.next());");
        builder.append("i ++;");
        builder.append("}");
        builder.append(" return t;}");
        return builder.toString();
    }

    static String arrayArrayExpressionCopyMethod(Type source,Type target) {
        String classCastName = BeanUtil.getClassCastName(target);
        String lastComponent = classCastName.substring(0, classCastName.indexOf("["));
        String wd = classCastName.substring(lastComponent.length());
        if (wd.startsWith("[]")) {
            wd = wd.substring(2);
        }
        StringBuilder builder = new StringBuilder("protected Object copy(Object source, Object target) {");
        builder.append(BeanUtil.getClassCastName(source));
        builder.append(" aSource = (").append(BeanUtil.getClassCastName(source)).append(") source;");
        builder.append("int length = aSource.length;");
        builder.append(classCastName).append(" t = new ").append(lastComponent).append("[length]").append(wd).append(";");
        builder.append("for(int i = 0;i<length;i++){");
        builder.append("t[i] = (").append(lastComponent).append(wd).append(")expression.express(aSource[i]);");
        builder.append("}");
        builder.append(" return t;}");
        return builder.toString();
    }

}
