package com.flask.mybatis.executor;

import com.flask.mybatis.MappedStatement;

import java.sql.SQLException;
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
     * 执行 select 类型的 sql 语句，返回结果为集合对象
     * @param ms
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> query(MappedStatement ms, Object parameter);

    int update(MappedStatement var1, Object var2) throws SQLException;

    //<E> List<E> query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4, CacheKey var5, BoundSql var6) throws SQLException;
    //
    //<E> List<E> query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4) throws SQLException;

    //<E> Cursor<E> queryCursor(MappedStatement var1, Object var2, RowBounds var3) throws SQLException;

    //List<BatchResult> flushStatements() throws SQLException;

    void commit(boolean var1) throws SQLException;

    void rollback(boolean var1) throws SQLException;

    // CacheKey createCacheKey(MappedStatement var1, Object var2, RowBounds var3, BoundSql var4);

    // boolean isCached(MappedStatement var1, CacheKey var2);

    void clearLocalCache();

    // void deferLoad(MappedStatement var1, MetaObject var2, String var3, CacheKey var4, Class<?> var5);

    // Transaction getTransaction();

    void close(boolean var1);

    boolean isClosed();

    void setExecutorWrapper(Executor var1);

}
