package com.flask.framework;

/**
 * Description:  BeanFactory是接口，提供了OC容器最基本的形式，给具体的IOC容器的实现提供了规范，
 *  在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public interface BeanFactory {
    Object getBean(String beanName);
}
