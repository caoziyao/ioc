package com.ccssy.sp;

import com.ccssy.sp.core.JsonApplicationContext;
import com.ccssy.sp.routes.*;
import com.ccssy.sp.server.*;
import com.ccssy.sp.server.control.HandlerExecutionChain;
import com.ccssy.sp.server.control.HandlerMapping;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * websocket
 * */

public class Main {


    public static void main(String[] args) throws IOException {

        HttpServer server = new HttpServer(8890);

        HandlerMapping hm = new HandlerMapping();

        JsonApplicationContext applicationContext = new JsonApplicationContext("application.json");
        applicationContext.init();

        try {
            Robot aiRobot = (Robot) applicationContext.getBean("robot");
            aiRobot.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {

            try {
                Socket socket = server.accept();
                byte[] req = server.read();

                Request request = new Request(req);
                Response response = new Response(socket);

                // mvc
                HandlerExecutionChain he = hm.getHandlerExecutionChain(request.path);
                HandlerAdapter ha = he.getHandler();

                // 304 Not Modified缓存支持

                // 执行处理器相关的拦截器的预处理（HandlerInterceptor.preHandle）
                he.applyPreHandle(request, response);

                ModelAndView mv = ha.handle(request, response);
                byte[] bytes = DispatcherServlet.viewFromResolver(mv);

                // 执行处理器相关的拦截器的后处理（HandlerInterceptor.postHandle）
                he.applyPostHandle(request, response);
                DispatcherServlet.render(response, bytes);

                // 执行处理器相关的拦截器的完成后处理（HandlerInterceptor.afterCompletion）
                he.triggerAfterCompletion(request, response);

                // close
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
