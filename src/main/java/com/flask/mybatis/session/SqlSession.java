package com.flask.mybatis.session;

import java.util.List;

/**
 * Description: 和数据库的会话，代表一次与数据库的连接
 * 是 mybatis 对外提供数据访问的主要 API
 * 实际上是基于 Excutor 来实现的
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public interface SqlSession {

    /**
     * 泛型方法，返回一个指定类型
     * @param statement
     * @param <T>
     * @return
     */
    <T> T selectOne(String statement);

    /**
     * 根据条件查询
     * @param statement
     * @param parameter
     * @param <T>
     * @return
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 范围查询
     * @param statement
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statement);
}
