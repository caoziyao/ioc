package com.flask.servlet;

import com.flask.BootStraper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
//        urls.add(new URL(""));

        // 为项目创建一个类加载器，加载项目的 class 和 jar 包
//        this.loader = new URLClassLoader(urls.toArray(new URL[]{}));
    }

    /**
     * 加载 路由
     * @return
     */
    public ProjectLoader loadAnnotation() {
        ClassLoader classLoader = BootStraper.class.getClassLoader();
        URL resource = classLoader.getResource("com/flask/app");
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            ProjectConfigBean projectConfigBean = BootStraper.projectConfigBeans.get(project);;
            for (File f : file.listFiles()) {
                String absolutePath = f.getAbsolutePath();
                absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                absolutePath = absolutePath.replace("/", ".");
                System.out.println(absolutePath);

                Class<?> clazz = null;
                try {
                    clazz = classLoader.loadClass(absolutePath);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (clazz.isAnnotationPresent(WebServlet.class)) {
                    WebServlet servletHandler = clazz.getAnnotation(WebServlet.class);
                    String route = servletHandler.value()[0];
                    System.out.println(route);

                    try {
                        HttpServlet o = (HttpServlet)clazz.newInstance();
                        projectConfigBean.set(route, o);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        File file = new File(resource.getFile());
        // 获取src资源文件编译后的路径（即classes路径）
//        System.out.println(file);
        return null;
    }

    public ProjectLoader load() {
        ProjectConfigBean configBean = BootStraper.projectConfigBeans.get(project);
//        configBean.s
        // 变量
//        // 3, 加载 和 初始化 servlet （启动时加载，可配置）
//        for (String project: projects) {
//            // servlet 加载，实例化，初始化
//        }
        return null;
    }
}
