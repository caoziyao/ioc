package com.ccssy.sp.server.view;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.utils.Utils;

public class TemplateView implements View {

    private ModelAndView mv;


    public TemplateView(ModelAndView mv) {
        this.mv = mv;
    }


    public byte[] render() {

        MyFileReader reader = new MyFileReader();

        String path = "src/main/resources/" + mv.getViewName();
        byte[] bytes = reader.readFileByByte(path);

        return bytes;
    }
}
