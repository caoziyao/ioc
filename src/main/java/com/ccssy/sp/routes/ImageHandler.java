package com.ccssy.sp.routes;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class ImageHandler implements BaseHandler {


    public void render(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
        byte[] str = reader.readFileByByte("src/main/resources/static/dog2.jpg");

        response.setContentType("image/gif");

        response.write(str);
    }

}
