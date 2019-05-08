package com.ccssy.sp.routes;

import com.ccssy.sp.TestAnnotation;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;


public class IndexController implements HandlerAdapter {


    public void init() {

    }


    public void destroy() {

    }


    public ModelAndView handle(Request request, Response response) {

        response.setContentType("text/html");

        ModelAndView mv = new ModelAndView();

        mv.setView("templates/index.html");
        mv.setModel(null);
        mv.setContentType("text/html");

        return mv;
    }


    @TestAnnotation
    public void test() {
        System.out.println("test hhhh ");
    }
}
