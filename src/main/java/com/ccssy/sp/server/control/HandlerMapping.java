package com.ccssy.sp.server.control;


import com.ccssy.sp.routes.*;
import com.ccssy.sp.server.Interceptor.DemoInterceptor;

import java.util.HashMap;

/**
 * HandlerMapping负责映射用户的URL和对应的处理类
 */
public class HandlerMapping {

    private HashMap<String, HandlerExecutionChain> map;


    public HandlerMapping() {
        initHandlerMappings();
    }


    private void initHandlerMappings() {
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
