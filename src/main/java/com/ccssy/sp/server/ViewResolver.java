package com.ccssy.sp.server;

import com.ccssy.sp.MyFileReader;

public class ViewResolver {

    public static byte[] viewFromName(String viewname) {
        String path = "src/main/resources/" + viewname;

        MyFileReader reader = new MyFileReader();
        byte[] bytes = reader.readFileByByte(path);

        return bytes;
    }
}
