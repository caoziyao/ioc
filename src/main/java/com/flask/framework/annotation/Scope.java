package com.flask.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:  bean作用域
 *  单例
 *  原型
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)  // 类
public @interface Scope {

    String value() default "singleton";
}
