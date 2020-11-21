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
public class IndexController {

    @Resource(name = "userService")
    private UserService userService;

//    @Resource(name = "orderService")
//    private OrderService orderService;

    @RequestMapping("/abc")
    public String abc(String name) {
//        orderService.say();
        userService.say();
        String username = userService.getName();
        return "hello:" + username + ":" + name;
    }
}
