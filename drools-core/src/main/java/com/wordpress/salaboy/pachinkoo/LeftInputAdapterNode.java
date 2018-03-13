/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

/**
 *
 * @author salaboy
 */
public class LeftInputAdapterNode extends LeftTupleSource implements ObjectSink {
    //HardCoded composite
    CompositeLeftTupleSinkAdapter sinkAdapter = new CompositeLeftTupleSinkAdapter();

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {
        propagationContext.getPropagatedHandles().add(factHandle);
        sinkAdapter.createAndPropagateAssertLeftTuple(factHandle, propagationContext, wm);

    }

    @Override
    public void addTupleSink(final LeftTupleSink tupleSink) {
        sinkAdapter.addTupleSink(tupleSink);
    }

}
