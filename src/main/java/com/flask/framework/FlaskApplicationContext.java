package com.flask.framework;

import com.flask.framework.annotation.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class FlaskApplicationContext {

    // 配置类
    private Class configClass;

    // 单例池
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    // bean 定义
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    /**
     * 该类被创建出来时候加载
     * @param configClass
     */
    public FlaskApplicationContext(Class configClass) {
        this.configClass = configClass;
        // BeanDefinition
        scan(configClass);
        instNonLaySingleton();


    }

    /**
     * 扫描所有注解的包
     * @param configClass
     */
    private void scan(Class configClass) {
        /**
         * 扫描包路径下面的 class 文件
         * 生成 BeanDefinition
         */
        // 拿到包路径
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();  // 包路径
            System.out.println(path);
            // com.flask.service -> com/flask/service
            path = path.replace(".", "/");


            /**
             * 得到 .class 文件
             */
            // 类加载器 sun.misc.Launcher$AppClassLoader@18b4aac2
            ClassLoader classLoader = FlaskApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());

            // 得到类
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    String absolutePath = f.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("/", ".");
                    // 加载扫描到的类
                    System.out.println(absolutePath);
                    try {
                        Class clazz = classLoader.loadClass(absolutePath);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            Component componentAnnotation = (Component) clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();

                            // 解析先生成 BeanDefinition
                            // singletonObjects.put(beanName, );
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
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
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
                Object bean = createBean(beanName, definition);
                singletonObjects.put(beanName, bean);
            }

        }
    }

    /**
     * 创建一个 bean
     * 实例 bean 的生命周期
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        // 1， 实例化  反射
        Class beanClass = beanDefinition.getBeanClass();
        try {
            Constructor declaredConstructor = beanClass.getDeclaredConstructor();
            Object instance = declaredConstructor.newInstance();

            // 2，依赖注入
            for (Field field : beanClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // todo byType byName
                    Object bean = getBean(field.getName());
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
    public Object getBean(String beanName) {
        //
        BeanDefinition definition = beanDefinitionMap.get(beanName);
        if (definition.getScope().equals("prototype")) {
            // 创建
            Object bean = createBean(beanName, definition);
            return bean;
        } else {
            if (singletonObjects.containsKey(beanName)) {
                return singletonObjects.get(beanName);
            } else {
                // 从单例池那对象
                Object o = addSingletonBean(beanName, definition);
                return o;
            }
        }
    }

    // 创建bean
    private Object addSingletonBean(String beanName, BeanDefinition definition) {
        Object bean = createBean(beanName, definition);
        singletonObjects.put(beanName, bean);
        return bean;
    }
}
