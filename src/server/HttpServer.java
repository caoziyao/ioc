package server;

import common.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    public void accept() {

        try {
            this.server = this.socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() throws IOException {
        this.server.close();
    }

    /**
     * read
     */
    public String read() throws IOException {

        Socket server = this.server;
        System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

        DataInputStream in = new DataInputStream(server.getInputStream());

//                System.out.println(in.readUTF());
        int count = in.available();
        while (count == 0) {
            count = in.available();
        }
        byte[] b = new byte[count];
        in.read(b);

        String s = Utils.byteArrayToStr(b);
        System.out.println("客户端发过来的内容:" + b.length + s);

        return s;
    }

    /**
     * write
     */
    public void write(String data) throws IOException {
        Socket server = this.server;
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String strRsp = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n"+data;
        byte[] rep = Utils.strToByteArray(strRsp);
        out.write(rep);
    }
}
