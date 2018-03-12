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

    public ObjectTypeNode(String type) {
        this.type = type;
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {
        if (this.type.equals(factHandle.getObject().getClass().getCanonicalName())) {
            sinkPropagator.propagateAssertObject(factHandle, null, propagationContext, wm);
        }
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
