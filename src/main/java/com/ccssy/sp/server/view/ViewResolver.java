package com.ccssy.sp.server.view;

import com.ccssy.sp.MyFileReader;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.utils.Utils;

public class ViewResolver {

    public static View viewFromMV(ModelAndView mv) {
        View view;

        if (mv.getContentType().equals("application/json")) {
            view = new JsonView(mv);
        } else {
            view = new TemplateView(mv);
        }

        return view;
    }
}
