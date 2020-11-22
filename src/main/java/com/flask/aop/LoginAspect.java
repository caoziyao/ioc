package com.flask.aop;

//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */

public class LoginAspect implements InvocationHandler {
    //被代理的对象
    Object targetObject;

    @Pointcut("login")
    public void pointCut() {

    }

    // @Before("login")
    public void before() {
        System.out.println("before 日记。。。");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        // 使用反射在目标对象上调用方法并传入参数
        Object result = method.invoke(targetObject, args);
        return result;
    }


    public Object getProxyObject(Object object) {
        this.targetObject = object;
        Object o = Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),  // 类加载器
                targetObject.getClass().getInterfaces(), // 获得被代理对象的所有接口
                this); // InvocationHandler对象
        return o;
    }

//    @Pointcut("execution(public * com.flask.service.UserService.*(..))")
//    public void pointCut() {
//
//    }
//
//    @Before("pointCut()")
//    public void beforeAdvice() {
//        System.out.println("before 日记。。。");
//    }
}
