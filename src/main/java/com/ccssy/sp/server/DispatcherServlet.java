package com.ccssy.sp.server;

import com.ccssy.sp.server.view.View;
import com.ccssy.sp.server.view.ViewResolver;
import com.ccssy.sp.utils.Utils;

/**
 * DispatcherServlet处理HTTP请求的工作流程：
 *
 * 1, 接受HTTP请求后，DispatcherServlet 会查询HandlerMapping以调用相应的Controller（根据请求的url）
 * 2, Controller接受请求并根据请求的类型Get/Post调用相应的服务方法，服务方法进行相应的业务处理，并设置模型数据，最后将视图名称返回给DispatcherServlet
 * 3, DispatcherServlet根据返回的视图名称从ViewResolver获取对应的视图
 * 4, DispatcherServlet将模型数据传递到最终的视图，并将视图返回给浏览器。
 * */
public class DispatcherServlet {

    public static void render(Response response, View view) {
        byte[] bytes = view.render();
        response.write(bytes);
    }
}
