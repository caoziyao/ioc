package com.ccssy.sp.routes;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public class JsonController implements HandlerAdapter {


    public void init() {

    }


    public void destroy() {

    }


    public ModelAndView handle(Request request, Response response) {
//        response.setContentType("application/json");
//        return "{555: 123}";

        response.setContentType("application/json");

        ModelAndView mv = new ModelAndView();

        mv.setView(null);
        mv.setModel("{555: 123}");
        mv.setContentType("application/json");

        return mv;
    }
}
