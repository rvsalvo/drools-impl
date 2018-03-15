/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

/**
 *
 * @author salaboy
 */
public class ObjectTypeNode extends ObjectSource implements ObjectSink {
    private final String type;
    private final ClassVariable classVariable;

    public ObjectTypeNode(String type) {
        this.type = type;
        this.classVariable = null;
    }

    public ObjectTypeNode(ClassVariable classVariable, String type) {
        this.type = type;
        this.classVariable = classVariable;
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {
        if (this.type.equals(factHandle.getObject().getClass().getCanonicalName())) {
            if (this.classVariable != null) {
                propagationContext.getBindingVariables().put(this.classVariable.getName(), factHandle.getObject());
            }
            sinkPropagator.propagateAssertObject(factHandle, null, propagationContext, wm);
        }
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
