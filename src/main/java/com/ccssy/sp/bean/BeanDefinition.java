package com.ccssy.sp.bean;

import java.util.List;

public class BeanDefinition {

    private String name;

    private String className;

    private String interfaceName;

    // 构造函数的传参的列表
    private List<String> constructorArgs;

    // 需要注入的参数列表 `propertyArgs
    private List<String> propertyArgs;


    public String getName() {
        return name;
    }


    public String getClassName() {
        return className;
    }


    public List<String> getConstructorArgs() {
        return null;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setClassName(String className) {
        this.className = className;
    }
}
