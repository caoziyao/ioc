package com.flask.framework;

import com.flask.framework.annotation.Component;
import com.flask.framework.annotation.RequestMapping;
import com.flask.framework.annotation.Resource;
import com.flask.framework.annotation.RestController;
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
    /**
     * 2, 初始化 bean 容器， bean容器（用来存放对象的）
     * 拿到扫描的类，通过反射实例化，并且放到 bean 容器中(beanName: bean)
     */
    private Map<String, Object> beanContain = new ConcurrentHashMap<String, Object>();
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
            loadConfig();
            // 2, 拿到扫描的类，通过反射实例化，并且放到 bean 容器中(beanName: bean)
            initContain(servletConfig);
            // 3，找到映射
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
//        Object[] parameters = method.getParameters();
        Object[] paramValues = new Object[method.getParameters().length];
        for (int i = 0; i < paramValues.length; i++) {
            Class<?> type = method.getParameters()[i].getType();
            if (ServletRequest.class.isAssignableFrom(type)) {
                paramValues[i] = req;
            } else if (ServletResponse.class.isAssignableFrom(type)) {
                paramValues[i] = resp;
            } else {
                String name = method.getParameters()[i].getName();
                paramValues[i] = req.getParameterMap().get(name)[0];
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
     * 2, 容器初始化
     *
     * @param config
     * @throws Exception
     */
    private void initContain(ServletConfig config) throws Exception {
        // 包 扫描
        String packagePath = scanBasePackage.replace(".", "/");
        final List<String> classFullNames = new ArrayList<String>();
        // /root/target/classes
        final Path rootPath = Paths.get(this.getClass().getResource("/").toURI());
        Path sourcePath = Paths.get(rootPath + "/" + packagePath);

        System.out.println(sourcePath);

        // 遍历目录下所有的文件
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".class")) {
                    // 把文件名转为类名 com.flask.xxxController
                    classFullNames.add(file.toString()
                            .replace(rootPath.toString() + "/", "")
                            .replaceAll("/", ".")
                            .replace(".class", "")
                    );

                }
                // 理解为替代递归方法
                return FileVisitResult.CONTINUE;
            }
        });
        System.out.println(classFullNames);
        // 找到类后，创建对象。 spring 对应注解
        for (String classFullName : classFullNames) {
            Class<?> clazz = Class.forName(classFullName);
            if (clazz.isAnnotationPresent(RestController.class) || clazz.isAnnotationPresent(Component.class)) {
                // 创建一个对象，保存到 spring 容器内， name 是首字母小写
                beanContain.put(toLowerFirstWord(clazz.getSimpleName()), clazz.newInstance());
                System.out.println(toLowerFirstWord(clazz.getSimpleName()));
            }
        }

        // 扫描整个类的属性，检查是否需要注入，需要则赋值。简单的 IOC 功能
        beanContain.forEach((name, bean) -> {
            // 过滤
            Arrays.stream(bean.getClass().getDeclaredFields())
                    .filter(field -> {
                        // 可以访问私有属性
                        field.setAccessible(true);
                        return field.getAnnotation(Resource.class) != null;
                    })
                    .forEach(field -> {
                        // 注入, 根据 resource 里面的名称 找到对应类
                        Resource annotation = field.getAnnotation(Resource.class);
                        String beanName = annotation.name();
                        try {
                            field.set(bean, beanContain.get(beanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    /**
     * 把字符串的首字母变小写
     */
    public String toLowerFirstWord(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        char firstChar = Character.toLowerCase(name.charAt(0));
        return firstChar + name.substring(1);
    }

    /**
     * 1，读取指定的配置文件
     * 注解解析
     * 拿到 scanBasePackage 路径
     *
     * @throws Exception
     */
    private void loadConfig() throws Exception {
        scanBasePackage = "com.flask";
    }
}
