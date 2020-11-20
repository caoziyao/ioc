package com.flask.servlet;

import com.flask.tomcat.BootStraper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 类加载工具
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class ProjectLoader {
    private String project;

    // JVM -- 动态加载
    URLClassLoader loader = null;

    public ProjectLoader(String project) throws MalformedURLException {
        this.project = project;
        // JVM class 文件和第三方 jar 包的存储路径
        List<URL> urls = new ArrayList<URL>();
        File libs = new File("jar文件全路径");
        if (libs.exists()) {
            for (String lib : libs.list()) {
                urls.add(new URL(lib));
            }
        }
        urls.add(new URL(""));

        this.loader = new URLClassLoader(urls.toArray(new URL[]{}));
    }

    public ProjectLoader load() {
        ProjectConfigBean projectConfigBean = BootStraper.projectConfigBeans.get(project);
        // 变量
//        // 3, 加载 和 初始化 servlet （启动时加载，可配置）
//        for (String project: projects) {
//            // servlet 加载，实例化，初始化
//        }
        return null;
    }
}
