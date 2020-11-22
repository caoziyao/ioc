package com.flask.mybatis.executor;

import com.flask.mybatis.Configuration;
import com.flask.mybatis.MappedStatement;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
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
    private Connection conn;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
        Configuration conf = configuration;

        try {
            //1.加载驱动程序
            Class.forName(configuration.getJdbcDriver());
            //2. 获得数据库连接
            this.conn = DriverManager.getConnection(conf.getJdbcUrl(), conf.getJdbcUsername(), conf.getJdbcPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        String sql = ms.getSql();
        System.out.println("执行：" + sql);

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //如果有数据，rs.next()返回true
            while (rs.next()) {
                System.out.println(rs.getString("username") + " 年龄：" + rs.getInt("age"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms) {
        List<E> ret = new ArrayList<>();

        String sql = ms.getSql();
        System.out.println("执行2：" + sql);

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 将结果集通过反射技术，填充到 list 中。
            handlerResultSet(rs, ret, ms.getResultType());
            return ret;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 将结果集通过反射技术，填充到 list 中。
     *
     * @param <E>
     * @return
     */
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String resultType) {
        try {
            Class<?> clazz = Class.forName(resultType);
            // 如果有数据，rs.next()返回true
            while (resultSet.next()) {
                Object o = clazz.newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String name = field.getName();
                    // 判断类型
                    if (int.class == field.getType()) {
                        field.set(o, resultSet.getInt(name));
                    } else {
                        field.set(o, resultSet.getString(name));
                    }
                }
                ret.add((E) o);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
