package com.flask.service;

import com.flask.framework.annotation.Autowired;
import com.flask.framework.annotation.Component;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@Component("userService")
public class UserService {
    @Autowired
    private OrderService orderService;

    public void say() {
        System.out.println(orderService);
        System.out.println("user say");
    }
    public String getName() {
        return "userrr";
    }
}
