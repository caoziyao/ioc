package com.flask.service;

import com.flask.framework.*;
import com.flask.framework.annotation.Autowired;
import com.flask.framework.annotation.Component;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@Component("orderService")
//@Scope("prototype")
//@Lazy
public class OrderService implements BeanNameAware, InitializingBean {
//    @Autowired
//    private UserService userService;

    private String beanName = "123";

    private String userName = "2234";

    public void say() {
//        System.out.println(userService);
        System.out.println("order say");
    }

    public void test() {
        System.out.println(userName);
        System.out.println(beanName);
//        System.out.println(userService);
    }


    @Override
    public void setBeanName(String beanName) {
//        this.beanName = beanName;

    }

    @Override
    public void afterPropertiesSet() {
//        this.userName = userService.getName();
    }
}
