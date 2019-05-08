package com.ccssy.sp.routes;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class NotFoundController implements HandlerAdapter {


    public void init() {

    }


    public void destroy() {

    }


    public ModelAndView handle(Request request, Response response) {
        response.setContentType("text/html");

        ModelAndView mv = new ModelAndView();

        mv.setView("templates/404.html");
        mv.setModel(null);
        mv.setContentType("text/html");

        return mv;
    }
}
