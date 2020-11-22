package com.flask.mybatis.bean;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class User {
    private int id;
    private String username;
    private String age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
