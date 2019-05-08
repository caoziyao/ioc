package com.ccssy.sp.routes;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class ImageController implements HandlerAdapter {


    public void init() {

    }


    public void destroy() {

    }


    public ModelAndView handle(Request request, Response response) {
        MyFileReader reader = new MyFileReader();
//        byte[] str = reader.readFileByByte("src/main/resources/static/dog2.jpg");

        response.setContentType("image/gif");

//        response.write(str);
        ModelAndView mv = new ModelAndView();

        mv.setView("static/dog2.jpg");
        mv.setModel(null);
        mv.setContentType("image/gif");

        return mv;
    }
}
