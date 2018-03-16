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
public class LeftTuple implements Tuple {

    private final LinkedList< Handle > handles = new LinkedList< Handle >();

    private final LeftTupleSink sink;

    private final Map< String, Object > bindVariables = new HashMap<>();


    public LeftTupleSink getSink() {

        return sink;
    }


    public LeftTuple( Handle handle, LeftTupleSink sink ) {

        this.sink = sink;
        this.bindVariables.putAll( bindVariables );
        this.handles.add( handle );
    }


    public LeftTuple( Handle handle, LeftTupleSink sink, Map< String, Object > bindVariables ) {

        this.sink = sink;
        this.handles.add( handle );
    }


    public LeftTuple( List< Handle > handles, LeftTupleSink sink ) {

        this.sink = sink;
        this.handles.addAll( handles );
    }


    public LeftTuple( List< Handle > handles, LeftTupleSink sink, Map< String, Object > bindVariables ) {

        this.sink = sink;
        this.bindVariables.putAll( bindVariables );
        this.handles.addAll( handles );
    }


    @Override
    public Handle get( int pattern ) {

        return handles.get( pattern );
    }


    @Override
    public List< Handle > getFactHandles() {

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


    /**
     * @return the bindVariables
     */
    public Map< String, Object > getBindVariables() {

        return bindVariables;
    }

}
