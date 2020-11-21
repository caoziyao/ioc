package com.flask.app;

import com.flask.framework.annotation.RequestMapping;
import com.flask.framework.annotation.Resource;
import com.flask.framework.annotation.RestController;
import com.flask.service.OrderService;
import com.flask.service.UserService;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
@RestController
@RequestMapping("/test")
public class IndextController {
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/abc")
    public String abc(String d) {
        return "abc" + d;
    }
}
