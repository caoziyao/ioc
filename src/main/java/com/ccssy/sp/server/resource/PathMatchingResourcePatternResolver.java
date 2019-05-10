package com.ccssy.sp.server.resource;


/**
 * 资源加载器
 * */
public class PathMatchingResourcePatternResolver {

    public Resource getResources(String path) {
        // 只有文件
        FileSystemResource fr = new FileSystemResource(path);
        return fr;
    }
}
