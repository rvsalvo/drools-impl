package com.wordpress.salaboy.pachinkoo.util;

import java.lang.reflect.Field;

/**
 * @author ezsalro
 *
 */
public class ReflectionUtils {

    public static Field getField(Class<?> clazz, String fieldName) {
        for (final Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

}
