package main.routes;

import main.MyFileReader;
import main.TestAnnotation;
import main.Utils;
import main.server.Request;
import main.server.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class IndexHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        final String fileName = "index.html";
        final String path = "src/resources/templates/" + fileName;

        byte[] str = reader.readFileByByte(path);

        response.setContentType("text/html");
        response.write(str);
    }

    @TestAnnotation
    public void test() {
        System.out.println("test hhhh ");
    }
}
