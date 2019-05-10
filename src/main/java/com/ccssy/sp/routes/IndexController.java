package com.ccssy.sp.routes;

import com.ccssy.sp.TestAnnotation;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;
import com.ccssy.sp.server.annotation.RequestMapping;


@RequestMapping(value = "/")
public class IndexController implements HandlerAdapter {

    public ModelAndView handle(Request request, Response response) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("templates/index.html");
        mv.setModel(null);
        mv.setContentType("text/html");

        response.setContentType("text/html");

        return mv;
    }


    @TestAnnotation
    public void test() {
        System.out.println("test hhhh ");
    }
}
