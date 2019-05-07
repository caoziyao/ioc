package main.routes;

import java.io.IOException;
import java.net.Socket;

public interface BaseHandler {

    byte[] render();

    void write(Socket server, byte[] data) throws IOException;
}
