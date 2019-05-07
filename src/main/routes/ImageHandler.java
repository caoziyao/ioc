package main.routes;

import main.MyFileReader;
import main.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ImageHandler implements BaseHandler {

    public byte[] render() {
        MyFileReader reader = new MyFileReader();
        byte[] str = reader.readFileByByte("src/resources/static/dog2.jpg");
        return str;
    }

    public void write(Socket server, byte[] data) throws IOException {
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        byte[] header = Utils.strToByteArray("HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\n\r\n");
        byte[] rep = Utils.byteMerger(header, data);

        out.write(rep);
    }
}