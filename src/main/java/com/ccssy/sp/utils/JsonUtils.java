package com.ccssy.sp.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.InputStream;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();


    private JsonUtils() {
    }


    public static ObjectMapper getObjectMapper() {
        return mapper;
    }


    public static <T> T readValue(String json, Class<T> cls) {

        try {
            return mapper.readValue(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> T readValue(InputStream is, Class<T> cls){
        try{
            return mapper.readValue(is,cls);
        }catch (Exception e){
            return null;
        }
    }


    public static <T> T readValue(byte[] bytes, Class<T> cls) {
        try {
            return mapper.readValue(bytes, cls);
        } catch (Exception var3) {
            return null;
        }
    }


    public static <T> T readValue(String json, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (Exception var3) {
            return null;
        }
    }


    public static <T> T readValue(byte[] bytes, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(bytes, valueTypeRef);
        } catch (Exception var3) {
            return null;
        }
    }


    public static <T> T readValue(InputStream is, TypeReference valueTypeRef){
        try{
            return mapper.readValue(is, valueTypeRef);
        }catch (Exception e){
            return null;
        }
    }


    public static String writeValue(Object entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (Exception var2) {
            return null;
        }
    }


    public static byte[] writeByteValue(Object entity) {
        try {
            return mapper.writeValueAsBytes(entity);
        } catch (Exception var2) {
            return null;
        }
    }

}
