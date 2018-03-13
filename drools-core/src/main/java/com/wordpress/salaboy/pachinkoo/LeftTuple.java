/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author salaboy
 */
public class LeftTuple implements Tuple {
    private final LinkedList<Handle> handles = new LinkedList<Handle>();
    private final LeftTupleSink sink;

    public LeftTupleSink getSink() {
        return sink;
    }

    public LeftTuple(Handle handle, LeftTupleSink sink) {
        this.sink = sink;
        handles.add(handle);
    }

    public LeftTuple(List<Handle> handles, LeftTupleSink sink) {
        this.sink = sink;
        handles.addAll(handles);
    }

    @Override
    public Handle get(int pattern) {
        return handles.get(pattern);
    }

    @Override
    public List<Handle> getFactHandles() {

        return handles;
    }

    @Override
    public int size() {
        return handles.size();
    }

    @Override
    public String toString() {
        return "LeftTuple{" + "handles=" + handles + ", sink=" + sink + '}';
    }

}
