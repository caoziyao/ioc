package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {

    private Integer port;
    private ServerSocket socket;
    private Socket server;

    public HttpServer(Integer port) {
        this.port = port;

        try {
            this.openSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * */
    public ServerSocket openSocket(Integer port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        this.socket = socket;

        return socket;

    }

    public Socket accept() {

        try {
            this.server = this.socket.accept();
            return this.server;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void close() throws IOException {
        this.server.close();
    }

    /**
     * read
     */
    public byte[] read() throws IOException {

        Socket server = this.server;
        System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

        DataInputStream in = new DataInputStream(server.getInputStream());

        int count = in.available();
        while (count == 0) {
            count = in.available();
        }
        byte[] b = new byte[count];
        in.read(b);

        System.out.println("客户端发过来的内容:" + b.length + Utils.byteArrayToStr(b));

        return b;
    }

//    /**
//     * write
//     */
//    public void write(String data) throws IOException {
//        Socket server = this.server;
//        DataOutputStream out = new DataOutputStream(server.getOutputStream());
//        String strRsp = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + data;
//        byte[] rep = Utils.strToByteArray(strRsp);
//        out.write(rep);
//    }
//
//
//    public void writeStatic(byte[] data) throws IOException {
//        Socket server = this.server;
//        DataOutputStream out = new DataOutputStream(server.getOutputStream());
////        byte[] header = Utils.strToByteArray("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
//        byte[] header = Utils.strToByteArray("HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\n\r\n");
//        byte[] rep = Utils.byteMerger(header, data);
//
//        out.write(rep);
//    }
//
//    public void writeTemplate(byte[] data) throws IOException {
//        Socket server = this.server;
//        DataOutputStream out = new DataOutputStream(server.getOutputStream());
//        byte[] header = Utils.strToByteArray("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
//        byte[] rep = Utils.byteMerger(header, data);
//
//        out.write(rep);
//    }
}
