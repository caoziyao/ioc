package com.ccssy.sp.server.resource;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 以文件系统路径查找的资源
 * */
public class FileSystemResource implements Resource {

    private final String path;


    public FileSystemResource(String path) {
        this.path = path;
    }


    public String getFilename() {
        return null;
    }


    public  byte[] readFileByByte(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream stream = new FileInputStream(file);

            int len = (int) file.length();
            byte[] buf = new byte[len];
            stream.read(buf);
            stream.close();

            return buf;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
