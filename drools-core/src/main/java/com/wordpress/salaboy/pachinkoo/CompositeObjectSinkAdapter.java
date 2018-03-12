/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public class CompositeObjectSinkAdapter implements ObjectSinkPropagator {
    private final List<ObjectSink> sinks = new ArrayList<ObjectSink>();

    @Override
    public void propagateAssertObject(Handle factHandle, Map<String, Object> bindingVariables, PropagationContext context, WorkingMemory wm) {
        for (final ObjectSink sink : sinks) {
            sink.assertObject(factHandle, context, wm);
        }
    }

    @Override
    public void propagateAssertObject(Handle factHandle, PropagationContext context, WorkingMemory wm) {
        for (final ObjectSink sink : sinks) {
            sink.assertObject(factHandle, context, wm);
        }
    }

    public void addObjectSink(ObjectSink sink) {
        sinks.add(sink);
    }

    @Override
    public List<ObjectSink> getSinks() {
        return sinks;
    }

    @Override
    public void addSinks(List<ObjectSink> sinks) {
        for (final ObjectSink sink : sinks) {
            this.addObjectSink(sink);
        }
    }

}
