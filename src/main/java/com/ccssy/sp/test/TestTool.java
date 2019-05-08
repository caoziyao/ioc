package com.ccssy.sp.test;

import com.ccssy.sp.TestAnnotation;
import com.ccssy.sp.routes.IndexHandler;

import java.lang.reflect.Method;

public class TestTool {


    public static void main(String[] args) {
        IndexHandler testobj = new IndexHandler();

        Class clazz = testobj.getClass();

        Method[] method = clazz.getDeclaredMethods();

        //用来记录测试产生的 log 信息
        StringBuilder log = new StringBuilder();

        // 记录异常的次数
        int errornum = 0;

        for (Method m: method) {
            if (m.isAnnotationPresent(TestAnnotation.class)) {
               try {
                   // setAccessible是启用和禁用访问安全检查的开关, 并不是为true就能访问为false就不能访问
                   m.setAccessible(true);
                   m.invoke(testobj, null);
               } catch (Exception e) {
                   errornum++;
                   log.append(m.getName());
                   log.append(" ");
                   log.append("has error:");
                   log.append("\n\r  caused by ");
                   //记录测试过程中，发生的异常的名称
                   log.append(e.getCause().getClass().getSimpleName());
                   log.append("\n\r");
                   //记录测试过程中，发生的异常的具体信息
                   log.append(e.getCause().getMessage());
                   log.append("\n\r");
               }
            }
        }

        log.append(clazz.getSimpleName());
        log.append(" has  ");
        log.append(errornum);
        log.append(" error.");

        // 生成测试报告
        System.out.println(log.toString());
    }
}
