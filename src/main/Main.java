package main;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Main {

    public static String render() {
        MyFileReader reader = new MyFileReader();
        String str = reader.readFileByChars("src/resources/index.html");
        return str;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        HttpServer server = new HttpServer(8890);
        while (true) {

            try {
                server.accept();
                String req = server.read();
                System.out.println("客户端发过来的内容:" + req);

                String data = render();
                server.write(data);
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
