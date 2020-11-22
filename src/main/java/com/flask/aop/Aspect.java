package com.flask.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)  // 类
public @interface Aspect {

    String value();
}
