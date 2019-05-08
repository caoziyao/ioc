package com.ccssy.sp.routes;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class NotFoundHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        final String fileName = "404.html";
        final String path = "src/main/resources/templates/" + fileName;

        byte[] str = reader.readFileByByte(path);

        response.setContentType("text/html");
        response.write(str);
    }
}
