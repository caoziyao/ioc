package com.flask.app;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import org.zel.*;
/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@WebServlet("/servlet/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.getMethod();
        try {
            resp.getOutputStream().write("hello  4444".getBytes());
            resp.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
