package com.wordpress.salaboy.pachinkoo;


import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wordpress.salaboy.pachinkoo.util.RestrictionUtils;


/**
 *
 * @author Rodrigo Salvo
 */
public class EvalAlphaNode extends ObjectSource implements ObjectSink {

    private final Object field;

    private final Object restriction;

    private final COMPARATOR comparator;


    public EvalAlphaNode( Object field, Object restriction, COMPARATOR comparator ) {

        this.field = field;
        this.restriction = restriction;
        this.comparator = comparator;
    }


    public Object getField() {

        return field;
    }


    public Object getRestriction() {

        return restriction;
    }


    public COMPARATOR getComparator() {

        return comparator;
    }


    @Override
    public long getId() {

        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public void assertObject( Handle factHandle, PropagationContext propagationContext, WorkingMemory wm ) {

        if ( factHandle == null ) {
            return;
        }

        try {
            RestrictionResult field =
                RestrictionUtils.getValueForConstraint( Arrays.asList( factHandle ), propagationContext.getBindingVariables(), this.field );

            Object fieldValue = field.getObject();

            RestrictionResult restriction =
                RestrictionUtils.getValueForConstraint( Arrays.asList( factHandle ), propagationContext.getBindingVariables(), this.restriction );

            Object restrictionValue = restriction.getObject();

            boolean shouldPropagate = false;
            switch ( comparator ) {
                case EQUAL:
                    if ( Objects.equals( fieldValue, restrictionValue ) ) {
                        shouldPropagate = true;
                    }
                    break;
                case NOT_EQUAL:
                    if ( !Objects.equals( fieldValue, restrictionValue ) ) {
                        shouldPropagate = true;
                    }
                    break;
            }
            if ( shouldPropagate ) {
                sinkPropagator.propagateAssertObject( factHandle, propagationContext, wm );
            }

        } catch ( final Exception ex ) {
            Logger.getLogger( EvalAlphaNode.class.getName() ).log( Level.SEVERE, null, ex );
        }

    }

}
