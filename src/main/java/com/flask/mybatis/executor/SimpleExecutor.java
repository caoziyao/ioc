package com.flask.mybatis.executor;

import com.flask.mybatis.Configuration;
import com.flask.mybatis.MappedStatement;

import java.util.List;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class SimpleExecutor implements Executor {
    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms) {
        return null;
    }
}
