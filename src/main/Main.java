package main;

import main.request.Request;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Main {

    public static String render() {
        MyFileReader reader = new MyFileReader();
        String str = reader.readFileByChars("src/resources/index.html");
        return str;
    }

    public static byte[] render2() {
        MyFileReader reader = new MyFileReader();
//        byte[] str = reader.readFileByByte("src/resources/index.html");
        byte[] str = reader.readFileByByte("src/resources/all.gif");
        return str;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        HttpServer server = new HttpServer(8890);
        while (true) {

            try {
                server.accept();
                byte[] req = server.read();

                Request request = new Request(req);

                byte[] data = render2();
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
