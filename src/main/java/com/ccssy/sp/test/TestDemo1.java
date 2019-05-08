package com.ccssy.sp.test;

import com.ccssy.sp.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TestDemo1 {

    public void demo() throws IOException {
        System.out.println("Hello World!");

        ServerSocket srvSocket = new ServerSocket(8890);
//        srvSocket.setSoTimeout(10000);

        while (true) {

            try {
                System.out.println("等待远程连接，端口号为：" + srvSocket.getLocalPort() + "...");

                Socket server = srvSocket.accept();
                System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(server.getInputStream());

                int count = in.available();
                while (count == 0) {
                    count = in.available();
                }
                byte[] b = new byte[count];
                in.read(b);
                System.out.println("客户端发过来的内容:" + b.length + Utils.byteArrayToStr(b));

                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                String strRsp = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\nhello1212";
                byte[] rep = Utils.strToByteArray(strRsp);

                out.write(rep);

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
