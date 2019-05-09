package com.ccssy.sp.server.view;

import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.utils.Utils;

public class JsonView implements View {

    private ModelAndView mv;


    public JsonView(ModelAndView mv) {
        this.mv = mv;
    }


    public byte[] render() {
        byte[] bytes = Utils.strToByteArray(mv.getModel());

        return bytes;
    }
}
