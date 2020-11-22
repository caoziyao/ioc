package com.flask.mybatis.session;

import com.flask.mybatis.Configuration;
import com.flask.mybatis.MappedStatement;
import com.flask.mybatis.executor.Executor;
import com.flask.mybatis.executor.SimpleExecutor;

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
     *
     * @param type
     * @return
     */
    public <T> T getMapper(Class<T> type) {
//        return (T) configuration.<T>getMappedStatementMap(type, this);
        return null;
    }

    @Override
    public <T> T selectOne(String statement) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        List<Object> selectList = executor.query(ms);
        if (selectList == null || selectList.size() == 0) {
            return null;
        }
        if (selectList.size() == 1) {
            return (T)selectList.get(0);
        } else {
            throw new RuntimeException("Too Many Results");
        }
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement) {
        return null;
    }
}
