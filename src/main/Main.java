package main;

import main.server.HttpServer;
import main.server.Request;
import main.routes.BaseHandler;
import main.routes.ImageHandler;
import main.routes.IndexHandler;
import main.routes.NotFoundHandler;
import main.server.Response;

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
        HashMap<String, BaseHandler> map = new HashMap<>();

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
