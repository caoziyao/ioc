package com.ccssy.sp.core;

import com.ccssy.sp.bean.BeanDefinition;
import com.ccssy.sp.utils.BeanUtils;
import com.ccssy.sp.utils.ClassUtils;
import com.ccssy.sp.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class BeanFactoryImpl implements BeanFactory {

    // beanName和实例化之后的对象
    private static final ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<String, Object>();

    // 对象的名称和对象对应的数据结构
    private static final ConcurrentHashMap<String, BeanDefinition> beanDefineMap = new ConcurrentHashMap<String, BeanDefinition>();

    private static final Set<String> beanNameSet = Collections.synchronizedSet(new HashSet<String>());


    public Object getBean(String name) throws Exception {
        //查找对象是否已经实例化过
        Object bean = beanMap.get(name);
        if (bean != null) {
            return bean;
        }

        //如果没有实例化，那就需要调用createBean来创建对象
        bean = createBean(beanDefineMap.get(name));
        if (bean != null) {
            //对象创建成功以后，注入对象需要的参数
            populatebean(bean);

            //再把对象存入Map中方便下次使用。
            beanMap.put(name, bean);
        }

        return bean;
    }


    // 对象的 BeanDefination 数据结构，存储起来
    protected void registerBean(String name, BeanDefinition bd) {
        beanDefineMap.put(name, bd);
        beanNameSet.add(name);
    }


    // 利用DeanDefination去实例化一个对象
    private Object createBean(BeanDefinition beanDefinition) throws Exception {
        String beanName = beanDefinition.getClassName();
        Class clz = ClassUtils.loadClass(beanName);
        if (clz == null) {
            throw new Exception("can not find bean by beanName");
        }

        // todo
        List<String> constructorArgs = beanDefinition.getConstructorArgs();
        if (constructorArgs != null && !constructorArgs.isEmpty()) {
            List<Object> objects = new ArrayList<Object>();
//            for (ConstructorArg constructorArg : constructorArgs) {
//                objects.add(getBean(constructorArg.getRef()));
//            }
            return BeanUtils.instanceByCglib(clz, clz.getConstructor(), objects.toArray());
        } else {
            return BeanUtils.instanceByCglib(clz, null, null);
        }
    }


    // 注入相应的参数
    private void populatebean(Object bean) throws Exception {
        Field[] fields = bean.getClass().getSuperclass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                String beanName = field.getName();
//                String beanName = field.get();
//                beanName = StringUtils.uncapitalize(beanName);
                beanName = beanName.toLowerCase();
                if (beanNameSet.contains(field.getName())) {
                    Object fieldBean = getBean(beanName);
                    if (fieldBean != null) {
                        ReflectionUtils.injectField(field, bean, fieldBean);
                    }
                }
            }
        }
    }
}
