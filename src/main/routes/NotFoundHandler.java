package main.routes;

import main.MyFileReader;
import main.Utils;
import main.server.Request;
import main.server.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NotFoundHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        final String fileName = "404.html";
        final String path = "src/resources/templates/" + fileName;

        byte[] str = reader.readFileByByte(path);

        response.setContentType("text/html");
        response.write(str);
    }
}
