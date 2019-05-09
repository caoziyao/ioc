package com.ccssy.sp.server;

import java.util.HashMap;

public class Request {

    private RequestParse requestParse;

    public String method;
    public String path;
    public String protocol;
    public Integer port;
    public String host;

    private HashMap<String, String> headers;
    private String body;


    public Request(byte[] request) {
        this.requestParse = new RequestParse();

        this.requestParse.parse(request, this);
    }


    public void setMethod(String method) {
        this.method = method;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }


    public void setBody(String body) {
        this.body = body;
    }


}
