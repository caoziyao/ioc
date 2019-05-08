package main.routes;

import main.MyFileReader;
import main.Utils;
import main.server.Request;
import main.server.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ImageHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        byte[] str = reader.readFileByByte("src/resources/static/dog2.jpg");

        response.setContentType("image/gif");

        response.write(str);
    }

}
