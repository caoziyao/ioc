package com.flask.mybatis;

import com.flask.mybatis.session.SqlSession;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class MapperRegistry {
    /**
     * 获取代理工厂实例
     * @param type
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
//        MapperProxyFactory<T> mapperProxyFactory =
        return null;
    }
}
