package com.flask.tomcat;

import java.io.IOException;
import java.net.Socket;

/**
 * Description: 具体处理请求
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class SocketProcessor implements Runnable {

    private Socket connection = null;

    public SocketProcessor(Socket connection) {
        this.connection = connection;
    }

    // @Override
    public void run() {
        try {
            byte[] requestBody = new byte[1024];
            connection.getInputStream().read(requestBody);
            System.out.println(new String(requestBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
