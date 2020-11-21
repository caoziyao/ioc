package com.flask.framework;

/**
 * Description: bean 定义
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class BeanDefinition {

    // 作用域
    private String scope;
    // 是否懒加载
    private Boolean isLazy;
    // bean 类型
    private Class beanClass;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getLazy() {
        return isLazy;
    }

    public void setLazy(Boolean lazy) {
        isLazy = lazy;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
