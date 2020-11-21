package com.flask.framework;

import com.flask.framework.annotation.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: DispatcherServlet 负责将请求分发，所有的请求都有经过它来统一分发
 * request -> Tomcat -> DispatcherServlet   url 匹配
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public class DispatcherServlet extends HttpServlet {
    //  1, 读取配置文件  包扫描路径
    private static String scanBasePackage = "";

    private FlaskApplicationContext flaskApplicationContext;
    /**
     * 2, 初始化 bean 容器， bean容器（用来存放对象的）
     * 拿到扫描的类，通过反射实例化，并且放到 bean 容器中(beanName: bean)
     */
     // private Map<String, Object> beanContain = new ConcurrentHashMap<String, Object>();
      private Map<String, Object> beanContain = null;
    /**
     * 3，保存 url 和 controller 对象以及对具体方法 method 的映射关系
     * 4，根据 URL 请求，调用具体的 controller 里面的方法
     */
    private Map<String, String> urlControllerMap = new ConcurrentHashMap<>();
    private Map<String, Method> urlMethodMapping = new ConcurrentHashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            // 1，加载配置文件
            loadConfig(AppConfig.class);
            flaskApplicationContext = new FlaskApplicationContext(AppConfig.class);
            // 2, 拿到扫描的类，通过反射实例化，并且放到 bean 容器中(beanName: bean)
            // initContain(servletConfig);
            // 3，找到映射
            beanContain = flaskApplicationContext.getControllerBean();
            initUrlHandlerMapping();
        } catch (Exception e) {

        }
    }

    /**
     * 处理请求
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1，根据 url 找到 handler ()
        String url = req.getServletPath();
        if (!this.urlControllerMap.containsKey(url)) {
            resp.getOutputStream().write("404".getBytes());
            return;
        }
        // 2，参数解析
        Method method = this.urlMethodMapping.get(url);
        // Object[] parameters = method.getParameters();
        Object[] paramValues = new Object[method.getParameters().length];
        for (int i = 0; i < paramValues.length; i++) {
            Class<?> type = method.getParameters()[i].getType();
            if (ServletRequest.class.isAssignableFrom(type)) {
                paramValues[i] = req;
            } else if (ServletResponse.class.isAssignableFrom(type)) {
                paramValues[i] = resp;
            } else {
                String name = method.getParameters()[i].getName();
                if (req.getParameterMap().get(name) == null) {
                    paramValues[i] = null;
                } else {
                    paramValues[i] = req.getParameterMap().get(name)[0];
                }

            } // TODO 对象绑定，参数路径。。。等等参数绑定功能
        }

        // 3，调用。反射
        try {
            String controllerName = this.urlControllerMap.get(url);
            Object result = method.invoke(this.beanContain.get(controllerName), paramValues);
            // 4， 返回结果
            if (result != null) {
                resp.getOutputStream().write(result.toString().getBytes());
                resp.getOutputStream().flush();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 4，返回结果
    }

    /**
     * 3，找到映射。保存 URL 和 controller 类及具体方法的映射关系
     * url : controller method
     *
     * @throws Exception
     */
    private void initUrlHandlerMapping() throws Exception {
        beanContain.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().isAnnotationPresent(RestController.class))
                .forEach(entry -> {
                    Class controllerClass = entry.getValue().getClass();
                    StringBuilder baseUrl = new StringBuilder();
                    if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping annotation = (RequestMapping) controllerClass.getAnnotation(RequestMapping.class);
                        baseUrl.append(annotation.value());
                    }
                    // 遍历所有方法，将方法和请求地址形成映射
                    Arrays.stream(controllerClass.getMethods())
                            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                            .forEach(method -> {
                                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                                String requestUrl = annotation.value();
                                requestUrl = baseUrl.append(requestUrl).toString();
                                // 映射： urlControllerMap 路径和类， urlMethodMapping 路径和方法
                                urlMethodMapping.put(requestUrl, method);
                                urlControllerMap.put(requestUrl, entry.getKey());
                            });
                });
    }

    /**
     * 1，读取指定的配置文件
     * 注解解析
     * 拿到 scanBasePackage 路径
     *
     * @throws Exception
     */
    private void loadConfig(Class configClass) throws Exception {
        // scanBasePackage = "com.flask";
        // 拿到包路径
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            scanBasePackage = componentScan.value();  // 包路径
        } else {
            throw new Exception("not scanBasePackage!");
        }
    }
}
