package com.ccssy.sp.routes;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;
import com.ccssy.sp.server.annotation.RequestMapping;


@RequestMapping(value = "/json")
public class JsonController implements HandlerAdapter {

    public ModelAndView handle(Request request, Response response) {

        response.setContentType("application/json");

        ModelAndView mv = new ModelAndView();
        mv.setViewName(null);
        mv.setModel("{555: 123}");
        mv.setContentType("application/json");

        return mv;
    }
}
