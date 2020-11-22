package com.flask.mybatis.session;

import com.flask.mybatis.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class SqlSessionFactoryBuilder {
    /**
     * build
     *
     * @param fileNmae
     * @return
     */
    public SqlSessionFactory build(String fileNmae) {
        SqlSessionFactoryBuilder.class.getClassLoader().getResource(".");
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileNmae);
        return build(inputStream);
    }

    public SqlSessionFactoryBuilder() {
    }

    /**
     * 生成 SqlSessionFactory 工厂
     *
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);

            // Configuration.PROPS.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactory(new Configuration(properties));
    }
}
