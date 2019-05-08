package com.ccssy.sp.server;

import com.ccssy.sp.routes.HandlerAdapter;
import com.ccssy.sp.routes.NotFoundController;
import com.ccssy.sp.utils.Utils;

import java.util.HashMap;

/**
 * DispatcherServlet处理HTTP请求的工作流程：
 *
 * 1, 接受HTTP请求后，DispatcherServlet 会查询HandlerMapping以调用相应的Controller（根据请求的url）
 * 2, Controller接受请求并根据请求的类型Get/Post调用相应的服务方法，服务方法进行相应的业务处理，并设置模型数据，最后将视图名称返回给DispatcherServlet
 * 3, DispatcherServlet根据返回的视图名称从ViewResolver获取对应的视图
 * 4, DispatcherServlet将模型数据传递到最终的视图，并将视图返回给浏览器。
 * */
public class DispatcherServlet {

//    public static HandlerAdapter getController(HashMap<String, HandlerAdapter> routes, String path) {
//
//        HandlerAdapter route = routes.get(path);
//        if (route == null) {
//            route = new NotFoundController();
//        }
//
//        return route;
//    }


    public static byte[] viewFromResolver(ModelAndView mv) {

        byte[] bytes;
        if (mv.contentType.equals("application/json")) {
            bytes = Utils.strToByteArray(mv.model);
        } else {
            bytes = ViewResolver.viewFromName(mv.view);
        }

        return bytes;
    }


    public static void render(Response response, byte[] bytes) {
        response.write(bytes);
    }
}
