package com.flask.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class WebServerStarter {
    // 线程池
    public final static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(25, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static void start() {
        try {
            int port = 9999;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(Thread.currentThread().getName() + "启动 " + port);
            // 循环获取连接对象
            while (true) {
                Socket connection = serverSocket.accept();
                // 线程池 - 具体处理请求
                threadPoolExecutor.execute(new SocketProcessor(connection));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServerStarter.start();
//        WebServerStarter webServerStarter = new WebServerStarter();
//        webServerStarter.start();
    }
}
