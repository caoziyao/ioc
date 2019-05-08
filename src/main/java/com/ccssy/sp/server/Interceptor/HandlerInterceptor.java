package com.ccssy.sp.server.Interceptor;


import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

/**
 * 拦截器
 * */
public interface HandlerInterceptor {

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 只有返回true才会继续向下执行，返回false取消当前请求
     * */
    boolean preHandle(Request request, Response response);


    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * */
    void postHandle(Request request, Response response);


    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * */
    void afterCompletion(Request request, Response response);
}
