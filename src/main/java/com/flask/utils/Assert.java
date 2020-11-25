package com.flask.utils;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/23
 */
public class Assert {
    public static <T> T notNull(T object, String errorMsg) throws IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException(errorMsg);
        }
        return object;
    }
}
