package com.ccssy.sp.test;

import com.ccssy.sp.Robot;
import com.ccssy.sp.core.JsonApplicationContext;

import java.io.IOException;

public class TestIoc {


    public static void testIoc() {
        JsonApplicationContext applicationContext = new JsonApplicationContext("application.json");
        applicationContext.init();

        try {
            Robot aiRobot = (Robot) applicationContext.getBean("robot");
            aiRobot.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        testIoc();
    }

}
