package main.routes;

import main.server.Request;
import main.server.Response;

import java.io.IOException;
import java.net.Socket;

public interface BaseHandler {

    void render(Request request, Response response);

//    void write(Socket server, byte[] data) throws IOException;
}
