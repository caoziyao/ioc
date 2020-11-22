package com.flask.app;

import com.flask.framework.annotation.RequestMapping;
import com.flask.framework.annotation.Resource;
import com.flask.framework.annotation.RestController;
import com.flask.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//    private IUserService userService;

    @RequestMapping("/abc")
    public String abc(HttpServletRequest request, HttpServletResponse response) {
        userService.login();
        userService.say();
//        IUserService user = (IUserService) new DynamicProxy().getProxyObject(userService);
//        user.login();
//        String username = (UserService)userService.getName();
        String name = request.getParameter("name");
        return "hello:" + ":" + name;
    }
}
