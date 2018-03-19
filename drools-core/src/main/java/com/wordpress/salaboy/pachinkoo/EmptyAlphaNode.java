package com.wordpress.salaboy.pachinkoo;


import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wordpress.salaboy.pachinkoo.util.ReflectionUtils;


/**
 *
 * @author Rodrigo Salvo
 */
public class EmptyAlphaNode extends ObjectSource implements ObjectSink {

    private ClassVariable classVariable;

    private String variable;

    private String field;


    public ClassVariable getClassVariable() {

        return classVariable;
    }


    public void setClassVariable( ClassVariable classVariable ) {

        this.classVariable = classVariable;
    }


    public EmptyAlphaNode( ClassVariable classVariable ) {

        this.classVariable = classVariable;
    }


    public EmptyAlphaNode( String variable, String field ) {

        this.variable = variable;
        this.field = field;
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
            if ( this.classVariable != null ) {
                propagationContext.getBindingVariables().put( this.classVariable.getName(), factHandle.getObject() );
            }
            if ( field != null ) {
                Field classField = ReflectionUtils.getField( factHandle.getObject().getClass(), field );
                if ( classField != null ) {
                    Object obj = classField.get( factHandle.getObject() );
                    if ( variable != null ) {
                        propagationContext.getBindingVariables().put( variable, obj );
                    }
                }
            }
            sinkPropagator.propagateAssertObject( factHandle, propagationContext, wm );

        } catch ( final Exception ex ) {
            Logger.getLogger( EmptyAlphaNode.class.getName() ).log( Level.SEVERE, null, ex );
        }

    }


    /**
     * @return the variable
     */
    public String getVariable() {

        return variable;
    }


    /**
     * @return the field
     */
    public String getField() {

        return field;
    }

}
