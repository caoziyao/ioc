package com.flask.servlet;

import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class ProjectConfigBean extends DefaultHandler {

//    // Servlet 集合
//    public Map<String, Servlet> servlets = new ConcurrentHashMap<String, Servlet>();

    // servlet 映射
    public Map<String, String> servletMapping;

    // Servlet 参数
    public Map<String, Map<String, String>> servletParam = new HashMap<String, Map<String, String>>();

    // Servlet 实例集合
    public Map<String, Servlet> servletInstances = new ConcurrentHashMap<String, Servlet>();

    public Map<String, Servlet> getServletInstance() {
        return null;
    }

    public ProjectConfigBean() {

    }
}
