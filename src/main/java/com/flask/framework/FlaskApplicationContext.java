package com.flask.framework;

import com.flask.aop.Aspect;
import com.flask.aop.Before;
import com.flask.aop.LoginAspectCG;
import com.flask.framework.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class FlaskApplicationContext implements BeanFactory {

    // 配置类
    private Class configClass;

    // bean 定义
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    /**
     * 单例池 一级缓存：用于存放完全初始化好的 bean
     **/
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    /**
     * 二级缓存：存放原始的 bean 对象（尚未填充属性）
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级级缓存：存放 bean 工厂对象，主要是防止重复aop
     */
    // private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>(16);
    private final Map<String, Object> singletonFactories = new HashMap<String, Object>();

    /**
     * 该类被创建出来时候加载
     *
     * @param configClass
     */
    public FlaskApplicationContext(Class configClass) throws Exception {
        this.configClass = configClass;
        initContain(configClass);
        instNonLaySingleton();
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
     * 2, 解析先生成 BeanDefinition
     *
     * @param configClass
     * @throws Exception
     */
    private void initContain(Class configClass) throws Exception {
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new Exception("没找到配置类");
        }
        ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScan.value();  // 包路径
        System.out.println(path);
        // com.flask.service -> com/flask/service
        String packagePath = path.replace(".", "/");

        // 包 扫描
        // String packagePath = scanBasePackage.replace(".", "/");
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
        // System.out.println(classFullNames);
        // 找到类后，创建对象。 spring 对应注解
        for (String classFullName : classFullNames) {
            try {
                // Class clazz = classLoader.loadClass(absolutePath);
                Class<?> clazz = Class.forName(classFullName);
                // Component 类
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component componentAnnotation = (Component) clazz.getAnnotation(Component.class);
                    String beanName = componentAnnotation.value();

                    // 解析先生成 BeanDefinition
                    BeanDefinition definition = new BeanDefinition();
                    definition.setBeanClass(clazz);
                    if (clazz.isAnnotationPresent(Scope.class)) {
                        Scope scopeAnnotation = (Scope) clazz.getAnnotation(Scope.class);
                        String value = scopeAnnotation.value();
                        definition.setScope(value);
                    } else {
                        definition.setScope("singleton");
                    }

                    if (clazz.isAnnotationPresent(Lazy.class)) {
                        definition.setLazy(true);
                    } else {
                        definition.setLazy(false);
                    }
                    beanDefinitionMap.put(beanName, definition);
                    System.out.println("put beanDefinitionMap:" + beanName);
                }

                // RestController 类
                if (clazz.isAnnotationPresent(RestController.class)) {
                    // 创建一个对象，保存到 spring 容器内， name 是首字母小写
                    BeanDefinition definition = new BeanDefinition();
                    definition.setScope("singleton");
                    definition.setLazy(false);
                    definition.setBeanClass(clazz);
                    beanDefinitionMap.put(toLowerFirstWord(clazz.getSimpleName()), definition);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实例化非懒加载的单例 bean
     */
    private void instNonLaySingleton() {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition definition = beanDefinitionMap.get(beanName);

            if (definition.getScope().equals("singleton") && !definition.getLazy()) {
                // 实例化
                Object bean = doCreateBean(beanName, definition);
                singletonObjects.put(beanName, bean);
            }

        }
    }

    /**
     * 判断是否存在循环依赖
     *
     * @return
     */
    private boolean isSingletonCurrentlyInCreation(Class beanClass) {
        // 2，依赖注入
        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建一个 bean
     * 实例 bean 的生命周期
     * 1, 从 一级缓存单例池 singletonObjects 获取对象，如果获取到则返回
     * 2，如果获取不到，则创建。
     * <p>
     * 创建：
     * 如果子属性有 @Autowired 放在二级缓存。
     * 如果子属性没有 @Autowired 放在一级缓存。
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        // 1， 实例化  反射
        Class beanClass = beanDefinition.getBeanClass();

        try {
            Constructor declaredConstructor = beanClass.getDeclaredConstructor();
            Object instance = declaredConstructor.newInstance();


            if (isSingletonCurrentlyInCreation(beanClass)) {
                // 放在二级缓存
                earlySingletonObjects.put(beanName, instance);
                System.out.println("put earlySingletonObjects:" + beanName);
            }

            for (Field field : beanClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // todo byType byName
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
                if (field.isAnnotationPresent(Resource.class)) {
                    Resource annotation = field.getAnnotation(Resource.class);
                    Object bean = getBean(annotation.name());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }

            // xxxAware接口
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // AOP
            if (beanClass.isAnnotationPresent(Aspect.class)) {
                Aspect proxyAnno = (Aspect) beanClass.getAnnotation(Aspect.class);
                String proxyClassPackage = proxyAnno.value();
                Class<?> proxyClazz = null;
                try {
                    proxyClazz = Class.forName(proxyClassPackage);
                    LoginAspectCG proxyInstance = (LoginAspectCG) proxyClazz.getDeclaredConstructor().newInstance();
                    instance = proxyInstance.getProxyObject(instance);
//                    LoginAspect proxyInstance = (LoginAspect)proxyClazz.getDeclaredConstructor().newInstance();
//                    IUserService s =  (IUserService)proxyInstance.getProxyObject(instance);
//                    instance = s;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                for (Method method : beanClass.getMethods()) {
                    if (method.isAnnotationPresent(Before.class)) {
//                        System.out.println("before------");
//                        Object proxyInstance = proxyClazz.newInstance();
                    }
                }
            }

            return instance;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建原型 bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        // 根据beanName获取 beanDefinition 对象
        final BeanDefinition definition = beanDefinitionMap.get(beanName);
        if (definition.getScope().equals("prototype")) {
            // 原型域bean的加载逻辑
            return doCreateBean(beanName, definition);
        } else {
            if (singletonObjects.containsKey(beanName)) {
                // 首先先尝试获取bean，如果加载过就不会在重复加载了
                return singletonObjects.get(beanName);
            } else if (earlySingletonObjects.containsKey(beanName)) {
                // 从二级缓存那
                return earlySingletonObjects.get(beanName);
            } else {
                // 从单例池那对象
                return addSingletonBean(beanName, definition);
            }
        }
    }


    /**
     * 创建bean
     *
     * @param beanName
     * @param definition
     * @return
     */
    private Object addSingletonBean(String beanName, BeanDefinition definition) {
        Object bean = doCreateBean(beanName, definition);
        singletonObjects.put(beanName, bean);
        System.out.println("put singletonObjects:" + beanName);
        if (earlySingletonObjects.containsKey(beanName)) {
            System.out.println("remove earlySingletonObjects:" + beanName);
            earlySingletonObjects.remove(beanName);
        }
        return bean;
    }

    /**
     * 返回 controller 类
     */
    public Map<String, Object> getControllerBean() {
        Map<String, Object> collect = singletonObjects.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().isAnnotationPresent(RestController.class))
                .collect(Collectors.toMap(
                        e -> e.getKey(), e -> e.getValue()
                ));
        return collect;
    }


    // 依赖注入
//            Arrays.stream(beanClass.getDeclaredFields())
//                    .filter(field -> {
//                        return field.isAnnotationPresent(Autowired.class)  ||
//                                field.isAnnotationPresent(Resource.class);
//                    })
//                    .forEach(field -> {
//                        if (field.isAnnotationPresent(Autowired.class)) {
//                            // todo byType byName
//                            Object bean = getBean(field.getName());
//                            field.setAccessible(true);
//                            try {
//                                field.set(instance, bean);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (field.isAnnotationPresent(Resource.class)) {
//                            Resource annotation = field.getAnnotation(Resource.class);
//                            Object bean = getBean(annotation.name());
//                            field.setAccessible(true);
//                            try {
//                                field.set(instance, bean);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
}
