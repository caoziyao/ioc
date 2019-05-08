package com.ccssy.sp.routes;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.TestAnnotation;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;


public class IndexHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        final String fileName = "index.html";
        final String path = "src/main/resources/templates/" + fileName;

        byte[] str = reader.readFileByByte(path);

        response.setContentType("text/html");
        response.write(str);
    }

    @TestAnnotation
    public void test() {
        System.out.println("test hhhh ");
    }
}
