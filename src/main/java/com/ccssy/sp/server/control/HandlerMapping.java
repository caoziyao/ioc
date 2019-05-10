package com.ccssy.sp.server.control;

import com.ccssy.sp.TestAnnotation;
import com.ccssy.sp.routes.*;
import com.ccssy.sp.server.Interceptor.DemoInterceptor;
import com.ccssy.sp.server.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * HandlerMapping负责映射用户的URL和对应的处理类
 */
public class HandlerMapping {

    private HashMap<String, HandlerExecutionChain> map;
    List<Class> adapters = new ArrayList<Class>();


    public HandlerMapping() {
        adapters.add(JsonController.class);
        adapters.add(IndexController.class);

        initHandlerMappings();
    }


    /**
     * 1, 拿到 controller  todo
     * 2, 拿到 注解
     * 3，拿到 注解参数
     * 4，生成 HandlerExecutionChain
     * */
    private void initHandlerMappings() {

        HashMap<String, HandlerExecutionChain> map = new HashMap<String, HandlerExecutionChain>();

        HandlerExecutionChain he3 = new HandlerExecutionChain(new ImageController());
        map.put("/static/all.gif", he3);

        for (Class clzz: this.adapters) {

            Annotation[] deAnnos = clzz.getDeclaredAnnotations();
            for (Annotation m: deAnnos) {
                if (m.annotationType().equals(RequestMapping.class)) {
                    RequestMapping rm = (RequestMapping)m;
                    System.out.println("register RequestMapping: " + rm.value());

                    try {
                        HandlerExecutionChain he = new HandlerExecutionChain((HandlerAdapter)clzz.newInstance());
                        map.put(rm.value(), he);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.map = map;
    }


    private void initHandlerMappings_backup() {
        HashMap<String, HandlerExecutionChain> map = new HashMap<String, HandlerExecutionChain>();

        HandlerExecutionChain he1 = new HandlerExecutionChain(new IndexController());
        he1.addInterceptor(new DemoInterceptor());

        HandlerExecutionChain he2 = new HandlerExecutionChain(new JsonController());
        HandlerExecutionChain he3 = new HandlerExecutionChain(new ImageController());

        map.put("/", he1);
        map.put("/json", he2);
        map.put("/static/all.gif", he3);

        this.map = map;
    }


    public HandlerExecutionChain getHandlerExecutionChain(String path) {
        HandlerExecutionChain he =  this.map.get(path);
        if (he == null) {
            he = new HandlerExecutionChain(new NotFoundController());
        }
        return he;
    }
}
