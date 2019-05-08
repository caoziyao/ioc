package com.ccssy.sp.server;

import com.ccssy.sp.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
