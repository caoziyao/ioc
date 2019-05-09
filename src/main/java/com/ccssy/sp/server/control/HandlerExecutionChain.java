package com.ccssy.sp.server.control;

import com.ccssy.sp.routes.HandlerAdapter;
import com.ccssy.sp.server.Interceptor.HandlerInterceptor;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理链
 * */
public class HandlerExecutionChain {

    private int interceptorIndex;
    private HandlerAdapter handler;
    List<HandlerInterceptor> interceptorList;


    public HandlerExecutionChain(HandlerAdapter handler) {
        this.handler = handler;
        interceptorList = new ArrayList<HandlerInterceptor>();
    }


    public HandlerAdapter getHandler() {
        return this.handler;
    }


    public void addInterceptor(HandlerInterceptor interceptor) {
        interceptorList.add(interceptor);
    }


    // 批量添加
    public void addInterceptors(HandlerInterceptor... interceptors) {
        interceptorList.addAll(Arrays.asList(interceptors));
    }


    // 依次执行HandlerInterceptor实现类的preHandle函数
    public boolean applyPreHandle(Request request, Response response) {

        if (interceptorList != null) {
            for (int i = 0; i < interceptorList.size(); i++) {
                HandlerInterceptor interceptor = interceptorList.get(i);
                boolean res = interceptor.preHandle(request, response);
                if (!res) {
                    return false;
                }

                this.interceptorIndex = i;
            }
        }
        return true;
    }


    // 执行HandlerInterceptor实现类的postHandle函数
    public void applyPostHandle(Request request, Response response) {

        if (interceptorList != null) {
            for (int i = interceptorList.size() - 1; i >= 0; i--) {
                HandlerInterceptor interceptor = interceptorList.get(i);
                interceptor.postHandle(request, response);
                this.interceptorIndex = i;
            }
        }
    }


    // 执行HandlerInterceptor实现类的afterCompletion函数
    public void triggerAfterCompletion(Request request, Response response) {

        if (interceptorList != null) {
            for (int i = interceptorList.size() - 1; i >= 0; i--) {
                HandlerInterceptor interceptor = interceptorList.get(i);
                interceptor.afterCompletion(request, response);
                this.interceptorIndex = i;
            }
        }
    }
}
