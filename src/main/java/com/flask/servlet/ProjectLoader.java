package com.flask.servlet;

import com.flask.BootStraper;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
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
        System.out.println(this.project);

        // 为项目创建一个类加载器，加载项目的 class 和 jar 包
        // this.loader = new URLClassLoader(urls.toArray(new URL[]{}));
    }

    /**
     * 加载 路由
     * @return
     */
    public ProjectLoader load() {
        ClassLoader classLoader = BootStraper.class.getClassLoader();
        URL resource = classLoader.getResource("com/flask/app");
        File file = new File(resource.getFile());

        // 遍历
        if (file.isDirectory()) {
            ProjectConfigBean configBean = BootStraper.projectConfigBeans.get(project);;
            for (File f : file.listFiles()) {
                String absolutePath = f.getAbsolutePath();
                absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                absolutePath = absolutePath.replace("/", ".");

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
                        // 实例化，反射
                        Servlet servlet = (Servlet)clazz.newInstance();
                        // TODO servlet 初始化
                        try {
                            servlet.init(new ServletConfig(){
                                @Override
                                public String getServletName() {
                                    return null;
                                }

                                @Override
                                public ServletContext getServletContext() {
                                    return null;
                                }

                                @Override
                                public String getInitParameter(String s) {
                                    return null;
                                }

                                @Override
                                public Enumeration<String> getInitParameterNames() {
                                    return null;
                                }
                            });
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }
                        // 保存对象
                        configBean.servletInstances.put(route, servlet);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }
}
