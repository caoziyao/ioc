package com.flask.mybatis.executor;

import com.flask.mybatis.MappedStatement;

import java.util.List;

/**
 * Description: 执行器
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public interface Executor {

    /**
     *
     * @param ms
     * @param <E>
     * @return
     */
    <E> List<E> query(MappedStatement ms);

    /**
     * 执行 select 类型的 sql 语句，返回结果为集合对象
     * @param ms
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> query(MappedStatement ms, Object parameter);

}
