/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public class RightTuple implements Tuple {

    private final LinkedList<Handle> handles = new LinkedList<Handle>();

    protected RightTupleSink sink;

    public RightTupleSink getSink() {
        return sink;
    }

    private final Map<String, Object> bindVariables = new HashMap<>();

    public RightTuple(Handle handle, RightTupleSink sink) {

        this.sink = sink;
        handles.add(handle);
    }

    public RightTuple(Handle handle, RightTupleSink sink, Map<String, Object> bindVariables) {

        this.sink = sink;
        this.bindVariables.putAll(bindVariables);
        handles.add(handle);
    }

    public RightTuple(List<Handle> handles, RightTupleSink sink, Map<String, Object> bindVariables) {

        this.sink = sink;
        this.bindVariables.putAll(bindVariables);
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

        return "RightTuple{" + "handles=" + handles + ", sink=" + sink + '}';
    }

    /**
     * @return the bindVariables
     */
    public Map<String, Object> getBindVariables() {

        return bindVariables;
    }

}
