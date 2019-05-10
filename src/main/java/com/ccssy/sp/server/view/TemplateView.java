package com.ccssy.sp.server.view;

import com.ccssy.sp.server.resource.MyFileReader;
import com.ccssy.sp.server.ModelAndView;
import com.ccssy.sp.server.resource.PathMatchingResourcePatternResolver;
import com.ccssy.sp.server.resource.Resource;

public class TemplateView implements View {

    private ModelAndView mv;


    public TemplateView(ModelAndView mv) {
        this.mv = mv;
    }


    public byte[] render() {

        MyFileReader reader = new MyFileReader();

        String path = "src/main/resources/" + mv.getViewName();
        PathMatchingResourcePatternResolver solver = new PathMatchingResourcePatternResolver();
        Resource res = solver.getResources(path);

        byte[] bytes = res.readFileByByte(path);

        return bytes;
    }
}
