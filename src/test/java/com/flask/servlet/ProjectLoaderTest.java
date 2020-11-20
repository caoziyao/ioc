package com.flask.servlet;

import java.net.MalformedURLException;


/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
class ProjectLoaderTest {

    public static void main(String[] args)  throws MalformedURLException {
        ProjectLoader projectLoader = new ProjectLoader("");
        projectLoader.loadAnnotation();
    }
}