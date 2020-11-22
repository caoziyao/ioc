package com.flask.mybatis;

import com.flask.utils.XmlUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

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
    private Configuration configuration;

    public SqlSessionFactory() {
        // 1, 解析总配置文件，填充到 Configuration
        loadDbInfo();
        // 2, 解析 mapper 配置文件
        // loadMapperInfo();
    }

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMapperInfo(Configuration.PROPS.getProperty("mapper.location"));
    }

    /**
     * 1, 解析总配置文件，填充到 Configuration
     */
    private void loadDbInfo() {

    }

    /**
     * 2, 解析 mapper 配置文件
     */
    private void loadMapperInfo(String mapperLocation) {
        mapperLocation = mapperLocation.replace(".", "/");
        String uri = Objects.requireNonNull(SqlSessionFactoryBuilder.class.getClassLoader().getResource(mapperLocation)).getPath();
        File file = new File(uri);
        if (!file.isDirectory()) {
            System.out.println("not directory！");
            return;
        }

        Path sourcePath = Paths.get(uri);
        // 遍历目录下所有的文件
        try {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".xml")) {
                        System.out.println(file);
                        XmlUtil.readMapperXml(file.toString(), configuration);
                    }
                    // 理解为替代递归方法
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开 SqlSession
     * @return
     */
    public SqlSession openSession() {
        return new SqlSession(this.configuration);
    }
}
