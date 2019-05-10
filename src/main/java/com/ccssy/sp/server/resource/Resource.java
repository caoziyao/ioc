package com.ccssy.sp.server.resource;



/**
 * 底层资源访问
 * 通过资源地址的特殊标识就可以访问相应的资源
 * */
public interface Resource {

    String getFilename();

    byte[] readFileByByte(String fileName);
}
