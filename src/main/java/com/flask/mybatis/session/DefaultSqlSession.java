package com.flask.mybatis.session;

import com.flask.mybatis.Configuration;
import com.flask.mybatis.MappedStatement;
import com.flask.mybatis.MapperProxy;
import com.flask.mybatis.executor.Executor;
import com.flask.mybatis.executor.SimpleExecutor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class DefaultSqlSession implements SqlSession {

    // config 配置，全局唯一
    private final Configuration configuration;

    // 底层依赖 executor 对象
    private final Executor executor;

    /**
     * @param configuration
     */
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    /**
     * 读取 mapper 对象
     * 读取配置文件 + 动态代理
     *
     * @param type UserMapper 接口
     * @return
     */
    public <T> T getMapper(Class<T> type) {
        MapperProxy mp = new MapperProxy(this);
        T mapper = (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mp); // InvocationHandler对象
        return mapper;
        // return (T) configuration.<T>getMappedStatementMap(type, this);
    }

    @Override
    public <T> T selectOne(String statement) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        List<T> selectList = executor.query(ms, null);
        if (selectList == null || selectList.size() == 0) {
            return null;
        }
        if (selectList.size() == 1) {
            return (T) selectList.get(0);
        } else {
            throw new RuntimeException("Too Many Results");
        }
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        //if (parameter instanceof List) {
            MappedStatement ms = configuration.getMappedStatement(statement);
            List<T> selectList = executor.query(ms, parameter);
            if (selectList == null || selectList.size() == 0) {
                return null;
            }
            if (selectList.size() == 1) {
                return (T) selectList.get(0);
            } else {
                throw new RuntimeException("Too Many Results");
            }
        //}
        //return null;
    }

    @Override
    public <T> List<T> selectList(String statement) {
        return null;
    }
}
