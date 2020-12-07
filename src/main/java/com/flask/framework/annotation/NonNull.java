package com.flask.framework.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *  A common Spring annotation to declare that annotated elements cannot be {@code null}.
 * @author csy
 * @version 1.0.0
 * @since 2020/11/25
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonNull {
}
