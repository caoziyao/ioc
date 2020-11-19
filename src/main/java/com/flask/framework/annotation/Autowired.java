package com.flask.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *   // CONSTRUCTOR 构造方法
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface Autowired {

    // true强制依赖注入，没有会报错。false不强制依赖注入
    boolean required() default true;
}
