package com.flask.framework.annotation;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface RequestMapping {
    String value() default "";
}
