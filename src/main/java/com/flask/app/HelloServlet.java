package com.flask.app;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // super.doGet(req, resp);
        req.getMethod();
        try {
            resp.getOutputStream().write("hello  4444".getBytes());
            resp.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
