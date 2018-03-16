package com.wordpress.salaboy.pachinkoo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.wordpress.salaboy.pachinkoo.ClassVariable;
import com.wordpress.salaboy.pachinkoo.FieldRestriction;
import com.wordpress.salaboy.pachinkoo.FieldVariable;
import com.wordpress.salaboy.pachinkoo.Handle;
import com.wordpress.salaboy.pachinkoo.RestrictionResult;

/**
 * @author ezsalro
 *
 */
public class RestrictionUtils {

    public static RestrictionResult getValueForConstraint(List<Handle> handles, Map<String, Object> bindingVariables, final Object constraint) {

        Object value = null;
        Handle resultHandle = null;
        ClassVariable classVariable = null;
        String var = null;

        if (constraint instanceof FieldRestriction) {
            final FieldRestriction restriction = (FieldRestriction) constraint;
            for (final Handle handle : handles) {
                if (handle.getObject().getClass() == restriction.getType()) {
                    final Field field = ReflectionUtils.getField(restriction.getType(), restriction.getField());
                    if (field == null) {
                        continue;
                    }
                    try {
                        value = field.get(handle.getObject());
                        resultHandle = handle;
                        var = restriction.getVariable();
                        classVariable = restriction.getClassVariable();
                        break;
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (constraint instanceof FieldVariable) {
            final FieldVariable variable = (FieldVariable) constraint;
            value = bindingVariables.get(variable.getVariable());
            if (value != null && variable.getMethod() != null) {
                try {
                    final Method method = ReflectionUtils.getMethod(value.getClass(), variable.getMethod());
                    if (method != null) {
                        value = method.invoke(value);
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            value = constraint;
        }
        return new RestrictionResult(value, resultHandle, classVariable, var);
    }

    public static Class<?> getFieldType(Map<String, Object> bindingVariables, Object field) {

        if (field instanceof FieldRestriction) {
            return ((FieldRestriction) field).getType();
        }
        if (field instanceof FieldVariable) {
            final Object obj = bindingVariables.get(((FieldVariable) field).getVariable());
            return obj.getClass();
        }

        return field.getClass();
    }

}
