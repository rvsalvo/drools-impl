/**
 * 
 */
package com.wordpress.salaboy.pachinkoo.rhs;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.wordpress.salaboy.pachinkoo.Handle;

/**
 * @author Rodrigo Salvo
 *
 */
public abstract class DefaultAction implements Action {

    protected String getResult(Collection<Handle> factHandles, Map<String, Object> bindVariables, String variable, String method, Object... args) {

        if (bindVariables != null) {
            Object obj = bindVariables.get(variable);
            if (obj != null) {
                if (obj instanceof Handle) {
                    obj = ((Handle) obj).getObject();
                }
                return getResult(factHandles, obj.getClass(), method, args);
            }
        }

        return null;
    }

    protected String getResult(Collection<Handle> factHandles, Class<?> clazz, String method, Object... args) {

        for (final Handle handle : factHandles) {
            if (clazz.isAssignableFrom(handle.getObject().getClass())) {
                Method m = null;
                try {
                    m = handle.getObject().getClass().getMethod(method);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                if (m != null) {
                    try {
                        return (String) m.invoke(handle.getObject());
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }
}
