package com.ccssy.sp.routes;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class ImageController implements HandlerAdapter {

    public ModelAndView handle(Request request, Response response) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("static/dog2.jpg");
        mv.setModel(null);
        mv.setContentType("image/gif");

        response.setContentType("image/gif");

        return mv;
    }
}
