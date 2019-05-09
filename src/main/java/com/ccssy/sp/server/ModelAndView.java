package com.ccssy.sp.server;

import java.util.HashMap;

public class ModelAndView {

    private String viewName;
    public String model;
    public String contentType;
    private HashMap<String, Object> mapped;


    public void addObject(String key, Object value) {
        mapped.put(key, value);
    }


    public void setViewName(String viewName) {
        this.viewName = viewName;
    }


    public String getViewName() {
        return viewName;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
