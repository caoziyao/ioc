package com.flask.mybatis;

/**
 * Description: 对接 jdbc 和 dao 层
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class SqlSession {

    private Configuration configuration;

    private Executor executor;

    public SqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    /**
     * 读取 mapper 对象
     * @param clazz
     * @return
     */
    public Object getMapper(Class clazz) {
        return null;
    }
}
