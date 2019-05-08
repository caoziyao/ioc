package com.ccssy.sp;

import com.ccssy.sp.core.JsonApplicationContext;
import com.ccssy.sp.server.HttpServer;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.routes.BaseHandler;
import com.ccssy.sp.routes.ImageHandler;
import com.ccssy.sp.routes.IndexHandler;
import com.ccssy.sp.routes.NotFoundHandler;
import com.ccssy.sp.server.Response;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class Main {


    public static String render() {
        MyFileReader reader = new MyFileReader();
        String str = reader.readFileByChars("src/resources/templates/index.html");
        return str;
    }


    private static HashMap<String, BaseHandler> registerRoutes() {
        HashMap<String, BaseHandler> map = new HashMap<String, BaseHandler>();

        map.put("/", new IndexHandler());
        map.put("/static/all.gif", new ImageHandler());

        return map;
    }


    public static BaseHandler getRoute(HashMap<String, BaseHandler> routes, String path) {

        BaseHandler route = routes.get(path);
        if (route == null) {
            route = new NotFoundHandler();
        }

        return route;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        HttpServer server = new HttpServer(8890);

        HashMap<String, BaseHandler> routes = registerRoutes();

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

                BaseHandler route = getRoute(routes, request.path);

                route.render(request, response);
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
