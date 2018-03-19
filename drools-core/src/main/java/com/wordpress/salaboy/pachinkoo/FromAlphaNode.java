package com.wordpress.salaboy.pachinkoo;


import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wordpress.salaboy.pachinkoo.util.RestrictionUtils;


/**
 *
 * @author Rodrigo Salvo
 */
public class FromAlphaNode extends ObjectSource implements ObjectSink {

    private ClassVariable classVariable;

    private Class< ? > type;

    private Object field;


    public ClassVariable getClassVariable() {

        return classVariable;
    }


    public void setClassVariable( ClassVariable classVariable ) {

        this.classVariable = classVariable;
    }


    public FromAlphaNode( ClassVariable classVariable, Class< ? > type, Object field ) {

        this.classVariable = classVariable;
        this.type = type;
        this.field = field;
    }


    public FromAlphaNode( Class< ? > type, Object field ) {

        this.type = type;
        this.field = field;
    }


    @Override
    public long getId() {

        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public void assertObject( Handle factHandle, PropagationContext propagationContext, WorkingMemory wm ) {

        try {

            RestrictionResult result = RestrictionUtils.getValueForConstraint( Arrays.asList( factHandle ), propagationContext.getBindingVariables(), field );

            if ( result.getObject() instanceof Collection< ? > ) {

                Collection< ? > coll = (Collection< ? >) result.getObject();

                for ( Object obj : coll ) {

                    PropagationContext newContext = new PropagationContext( propagationContext.getBindingVariables() );

                    if ( this.classVariable != null ) {
                        newContext.getBindingVariables().put( this.classVariable.getName(), obj );
                    }
                    sinkPropagator.propagateAssertObject( new FactHandle( obj ), newContext, wm );

                }
            }

        } catch ( final Exception ex ) {
            Logger.getLogger( FromAlphaNode.class.getName() ).log( Level.SEVERE, null, ex );
        }

    }


    /**
     * @return the field
     */
    public Object getField() {

        return field;
    }


    /**
     * @return the type
     */
    public Class< ? > getType() {

        return type;
    }

}
