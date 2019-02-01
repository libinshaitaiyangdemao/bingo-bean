package com.jd.bingo.bean.mapper.utils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.NotFoundException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/4/26 20:32
 * @lastdate:
 */
public class ClassAuthor {
    private AtomicInteger count;

    private static class ClassAuthorHolder {
        private static final ClassAuthor ca = new ClassAuthor();
    }

    private ClassPool classPool;

    private ClassAuthor() {
        this.classPool = new ClassPool(true);
        count = new AtomicInteger(0);
    }

    public static ClassAuthor getInstance() {
        return ClassAuthorHolder.ca;
    }

    public Class create(String name, Class supers, String... methods) throws CannotCompileException, NotFoundException {
        CtClass ctc = classPool.makeClass(name + "_byca_" + count.getAndIncrement());
        ctc.addConstructor(CtNewConstructor.defaultConstructor(ctc));
        ctc.setSuperclass(classPool.get(supers.getName()));
        if (methods != null) {
            for (String m : methods) {
                ctc.addMethod(CtMethod.make(m, ctc));
            }
        }
        return ctc.toClass(Thread.currentThread().getContextClassLoader());
    }
}
