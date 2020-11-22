package com.flask.mybatis;

import com.flask.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Description: mapper 代理
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    // private Class<T> mapperInterface;

    /**
     *
     * @param sqlSession sql 链接
     */
    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        // this.mapperInterface = mapperInterface;
    }

//    public MapperProxy() {
//    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        String statement = method.getDeclaringClass().getName() + "." + method.getName();
        if (Collections.class.isAssignableFrom(returnType)) {
            // 返回值是 Collections 子类，例如 List
            return sqlSession.selectList(statement);
        }
        return sqlSession.selectOne(statement);
    }
}
