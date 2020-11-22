package com.flask.mybatis;

import com.flask.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description: mapper 代理
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    private Class<T> mapperInterface;

    /**
     *
     * @param sqlSession sql 链接
     * @param mapperInterface 接口
     */
    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
