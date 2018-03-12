/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public interface ObjectSinkPropagator {
    void propagateAssertObject(Handle factHandle, Map<String, Object> bindingVariable, PropagationContext context, WorkingMemory wm);

    void propagateAssertObject(Handle factHandle, PropagationContext context, WorkingMemory wm);

    List<ObjectSink> getSinks();

    void addSinks(List<ObjectSink> sinks);
}
