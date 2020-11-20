package com.flask;

import com.flask.servlet.ProjectChecker;
import com.flask.servlet.ProjectConfigBean;
import com.flask.servlet.ProjectLoader;
import com.flask.tomcat.WebServerStarter;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 启动引导类
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class BootStraper {
    /**
     * 工作空间 - 也就是 war 包的发布目录
     * @param args
     */
    public static final String workSpace = "./";

    public static  Map<Object, ProjectConfigBean> projectConfigBeans = new ConcurrentHashMap<Object, ProjectConfigBean>();

    public static void main(String[] args) {
        ProjectLoader projectLoader = null;
        try {
            projectLoader = new ProjectLoader("flask");
            BootStraper.projectConfigBeans.put("flask", new ProjectConfigBean());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        projectLoader.load();
//        // 1, 检查目录下是否有项目部署
//        Set<String> projects = ProjectChecker.check(workSpace);
//        if (projects != null) {
//            for (String project: projects) {
//                ProjectConfigBean projectConfigBean = new ProjectConfigBean();
//                projectConfigBeans.put(project,  projectConfigBean);
//            }
//
//        }
//        // 2, 解析 web.xml
//
//        // 服务启动
        WebServerStarter.start();
    }
}
