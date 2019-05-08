package com.ccssy.sp.routes;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public interface HandlerAdapter {

    void init();

    void destroy();

    ModelAndView handle(Request request, Response response);
}
