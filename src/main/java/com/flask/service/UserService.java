package com.flask.service;

import com.flask.aop.Aspect;
import com.flask.aop.Before;
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
@Aspect("com.flask.aop.LoginAspect")
public class UserService implements IUserService {
//    @Autowired
//    private OrderService orderService;

    @Before
    public void login() {
        System.out.println("login...");
    }

    public void say() {
//        System.out.println(orderService);
        System.out.println("user say");
    }
    public String getName() {
        return "userrr";
    }
}
