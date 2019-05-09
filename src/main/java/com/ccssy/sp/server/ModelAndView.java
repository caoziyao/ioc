package com.ccssy.sp.server;

import lombok.Data;
import java.util.HashMap;

@Data
public class ModelAndView {

    private String viewName;
    private String model;
    private String contentType;
    private HashMap<String, Object> mapped;


    public void addObject(String key, Object value) {
        mapped.put(key, value);
    }
}
