
import common.Utils;
import server.HttpServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Main {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");


        HttpServer server = new HttpServer(8890);

        while (true) {

            try {
                server.accept();
                String req = server.read();
                System.out.println("客户端发过来的内容:" + req);
                server.write("hello1212ssa");
                server.close();


            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }


    }
}
