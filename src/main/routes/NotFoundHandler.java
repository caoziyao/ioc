package main.routes;

import main.MyFileReader;
import main.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NotFoundHandler implements BaseHandler {

    public byte[] render() {
        MyFileReader reader = new MyFileReader();
        final String fileName = "404.html";
        final String path = "src/resources/templates/" + fileName;

        byte[] str = reader.readFileByByte(path);
        return str;
    }

    public void write(Socket server, byte[] data) throws IOException {
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        byte[] header = Utils.strToByteArray("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
        byte[] rep = Utils.byteMerger(header, data);

        out.write(rep);
    }
}
