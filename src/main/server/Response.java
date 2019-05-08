package main.server;

import main.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Response {

    private Socket server;
    private String contentType = "text/html";


    public Response(Socket server) {
        this.server = server;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public void write(byte[] data) {
        Socket server = this.server;

        try {
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            String s = "HTTP/1.1 200 OK\r\nContent-Type: " + this.contentType + "\r\n\r\n";

            byte[] header = Utils.strToByteArray(s);
            byte[] rep = Utils.byteMerger(header, data);
            out.write(rep);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
