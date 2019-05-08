package com.ccssy.sp.routes;

import com.ccssy.sp.server.Request;
import com.ccssy.sp.server.Response;

public interface BaseHandler {

    void render(Request request, Response response);

//    void write(Socket server, byte[] data) throws IOException;
}
