package com.wordpress.salaboy.pachinkoo.util;


import java.lang.reflect.Field;

import com.wordpress.salaboy.pachinkoo.FieldRestriction;
import com.wordpress.salaboy.pachinkoo.FieldVariable;
import com.wordpress.salaboy.pachinkoo.Handle;
import com.wordpress.salaboy.pachinkoo.PropagationContext;
import com.wordpress.salaboy.pachinkoo.Tuple;


/**
 * @author ezsalro
 *
 */
public class RestrictionUtils {

    public static Object getValueForConstraint( Tuple Tuple, PropagationContext propagationContext, final Object constraint ) {

        Object value = null;
        if ( constraint instanceof FieldRestriction ) {
            final FieldRestriction restriction = (FieldRestriction) constraint;
            for ( final Handle handle : Tuple.getFactHandles() ) {
                if ( handle.getObject().getClass() == restriction.getType() ) {
                    final Field field = ReflectionUtils.getField( restriction.getType(), restriction.getField() );
                    if ( field == null ) {
                        continue;
                    }
                    try {
                        value = field.get( handle.getObject() );
                        break;
                    } catch ( final Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        } else if ( constraint instanceof FieldVariable ) {
            final FieldVariable variable = (FieldVariable) constraint;
            value = propagationContext.getBindingVariables().get( variable.getVariable() );
        } else {
            value = constraint;
        }
        return value;
    }


    public static Class< ? > getFieldType( PropagationContext propagationContext, Object field ) {

        if ( field instanceof FieldRestriction ) {
            return ( (FieldRestriction) field ).getType();
        }
        if ( field instanceof FieldVariable ) {
            Object obj = propagationContext.getBindingVariables().get( ( (FieldVariable) field ).getVariable() );
            return obj.getClass();
        }

        return field.getClass();
    }

}
