package com.ccssy.sp.server;

public class ModelAndView {

    public String view;

    public String model;

    public String contentType;


    public void setView(String view) {
        this.view = view;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
