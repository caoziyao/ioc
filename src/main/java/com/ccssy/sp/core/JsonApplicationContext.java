package com.ccssy.sp.core;

import com.ccssy.sp.bean.BeanDefinition;
import com.ccssy.sp.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;

public class JsonApplicationContext extends BeanFactoryImpl {

    private String fileName;


    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;
    }


    public void init(){
        loadFile();
    }


    /**
     *  读取配置文件
     *  将配置文件转换为容器能够理解的 BeanDefination。
     *  使用 registerBean 方法。注册这个对象。
     * */
    private void loadFile() {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        List<BeanDefinition> beanDefinitions = JsonUtils.readValue(is, new TypeReference<List<BeanDefinition>>(){});
        if(beanDefinitions != null && !beanDefinitions.isEmpty()) {
            for (BeanDefinition b : beanDefinitions) {
                registerBean(b.getName(), b);
            }
        }
    }
}
