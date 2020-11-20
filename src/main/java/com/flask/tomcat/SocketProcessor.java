package com.flask.tomcat;

import com.flask.BootStraper;
import com.flask.servlet.ProjectConfigBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Override
    public void run() {
        try {
            // 接收数据
            byte[] requestBody = new byte[1024];
            connection.getInputStream().read(requestBody);
            System.out.println(new String(requestBody));

            // request response 对象转化
            HttpServletRequest request = HttpFactory.createRequest(requestBody);
            HttpServletResponse response = HttpFactory.createResponse(connection);

            // 寻找 servlet
            ProjectConfigBean projectConfigBean = BootStraper.projectConfigBeans.get("flask");
            Servlet servlet = projectConfigBean.servletInstances.get(request.getRequestURI());
             if (servlet == null) {
                response.getOutputStream().write("404".getBytes());
                return;
            }

            servlet.service(request, response);

        } catch (ServletException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
