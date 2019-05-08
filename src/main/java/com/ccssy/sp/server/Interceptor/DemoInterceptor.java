package com.ccssy.sp.server.Interceptor;

import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class DemoInterceptor implements HandlerInterceptor {


    public boolean preHandle(Request request, Response response) {
        System.out.println("demo preHandle");
        return true;
    }


    public void postHandle(Request request, Response response) {
        System.out.println("demo postHandle");
    }


    public void afterCompletion(Request request, Response response) {
        System.out.println("demo afterCompletion");
    }
}
