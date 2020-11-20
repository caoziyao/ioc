package com.flask.tomcat;

import javax.servlet.http.*;
import java.net.Socket;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class HttpFactory {

    /**
     * 创建一个 HttpServletRequest 对象
     * @param requestBody 请求上下文
     * @return
     */
    public static HttpServletRequest createRequest(final byte [] requestBody) {
        return new RequestFacade(requestBody);
    }

    /**
     * 实现 HttpServletResponse
     * @return
     */
    public static HttpServletResponse createResponse(final Socket connection) {
        return new ResponseFacade(connection);
    }
}
