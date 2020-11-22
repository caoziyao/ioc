package com.flask.mybatis;

import com.flask.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Description: 总配置信息
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public class Configuration {

    /**
     * 配置项
     */
    public static Properties PROPS;

    private String jdbcDriver;

    private String jdbcUrl;

    private String jdbcUsername;

    private String jdbcPassword;

    /**
     * 读取配置信息
     * @param properties
     */
    public Configuration(Properties properties) {
        Configuration.PROPS = properties;
        jdbcDriver = properties.getProperty("spring.datasource.driver-class-name");
        jdbcUrl = properties.getProperty("spring.datasource.url");
        jdbcUsername = properties.getProperty("spring.datasource.username");
        jdbcPassword = properties.getProperty("spring.datasource.password");
    }

    /**
     * mapper 代理注册
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry();

    /**
     * key: namespace + sourceId
     * value: sql的封装对象
     */
    protected Map<String, MappedStatement> mappedStatementMap = new HashMap<>();


    /**
     * 获取 Mapper
     * @param type
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.mapperRegistry.getMapper(type, sqlSession);
    }

    /**
     * 获取 MappedStatement
     * @param statement
     * @return
     */
    public MappedStatement getMappedStatement(String statement) {
        return mappedStatementMap.get(statement);
    }


    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

    public void setMapperRegistry(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

}
