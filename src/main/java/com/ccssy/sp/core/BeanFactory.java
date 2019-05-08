package com.ccssy.sp.core;

public interface BeanFactory {

    // 目前只支持一种 ByName 的注入
    Object getBean(String name) throws Exception;
}
