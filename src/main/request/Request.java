package main.request;

import java.util.HashMap;

public class Request {

    private RequestParse requestParse;

    private String method;
    private String path;
    private String protocol;
    private Integer port;
    private String host;

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


}
