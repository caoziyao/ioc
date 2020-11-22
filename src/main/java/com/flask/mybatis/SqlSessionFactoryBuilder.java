package com.flask.mybatis;

import java.io.IOException;
import java.io.InputStream;

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
     * @param fileNmae
     * @return
     */
    public SqlSessionFactory build(String fileNmae) {
        SqlSessionFactoryBuilder.class.getClassLoader().getResource(".");
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileNmae);
        return build(inputStream);
    }

    /**
     * 生成 SqlSessionFactory 工厂
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) {
        try {
            Configuration.PROPS.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactory(new Configuration());
    }
}
