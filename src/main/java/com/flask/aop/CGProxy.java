package com.flask.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class CGProxy implements MethodInterceptor {
    // 被代理对象
    Object targetObject;

    /**
     * 动态生成一个新的类，使用父类的无参构造方法创建一个指定了特定回调的代理实例
     *
     * @param object
     * @return
     */
    public Object getProxyObject(Object object) {
        this.targetObject = object;
        // 增强器，动态代码生成器
        Enhancer enhancer = new Enhancer();
        // 回调方法
        enhancer.setCallback(this);
        // 设置生成类的父类类型
        enhancer.setSuperclass(targetObject.getClass());
        //动态生成字节码并返回代理对象
        return enhancer.create();
    }

    /**
     * 拦截方法
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 被织入的横切内容，开始时间 before
        long start = System.currentTimeMillis();
        Object result = methodProxy.invoke(targetObject, objects);
        Long span = System.currentTimeMillis() - start;
        System.out.println("cg共用时：" + span);
        return result;
    }
}
