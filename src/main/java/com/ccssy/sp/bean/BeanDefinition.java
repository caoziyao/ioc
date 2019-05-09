package com.ccssy.sp.bean;

import lombok.Data;

import java.util.List;

@Data
public class BeanDefinition {

    private String name;

    private String className;

    private String interfaceName;

    // 构造函数的传参的列表
    private List<String> constructorArgs;

    // 需要注入的参数列表 `propertyArgs
    private List<String> propertyArgs;


    public List<String> getConstructorArgs() {
        return null;
    }
}
