package com.ccssy.sp.utils;

/**
 * 负责处理 Java 类的加载
 * */
public class ClassUtils {


    public static ClassLoader getDefultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    public static Class loadClass(String className) {

        try {
           return getDefultClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
