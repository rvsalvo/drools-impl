package com.wordpress.salaboy.pachinkoo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo Salvo
 */
public class EmptyAlphaNode extends ObjectSource implements ObjectSink {

    private ClassVariable classVariable;

    public ClassVariable getClassVariable() {
        return classVariable;
    }

    public void setClassVariable(ClassVariable classVariable) {
        this.classVariable = classVariable;
    }

    public EmptyAlphaNode(ClassVariable classVariable) {
        this.classVariable = classVariable;
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {
        try {
            if (this.classVariable != null) {
                propagationContext.getBindingVariables().put(this.classVariable.getName(), factHandle.getObject());
            }
            sinkPropagator.propagateAssertObject(factHandle, propagationContext, wm);

        } catch (final Exception ex) {
            Logger.getLogger(EmptyAlphaNode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
