package com.flask.mybatis;

/**
 * Description:
 * 1，初始化配置文件到内存
 * 2，作为工厂类创建 sqlSession
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public class SqlSessionFactory {

    /**
     * 总配置信息
     * final
     */
    private final Configuration configuration = new Configuration();

    public SqlSessionFactory() {
        // 1, 解析总配置文件，填充到 Configuration
        loadDbInfo();
        // 2, 解析 mapper 配置文件
        loadMapperInfo();
    }

    /**
     * 1, 解析总配置文件，填充到 Configuration
     */
    private void loadDbInfo() {

    }

    /**
     * 2, 解析 mapper 配置文件
     */
    private void loadMapperInfo() {

    }
}
