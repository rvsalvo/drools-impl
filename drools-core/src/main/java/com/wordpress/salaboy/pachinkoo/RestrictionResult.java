package com.wordpress.salaboy.pachinkoo;

/**
 * 
 * @author ezsalro
 *
 */
public class RestrictionResult {

    private final Object object;
    private final Handle handle;

    public RestrictionResult(Object object, Handle handle) {
        super();
        this.object = object;
        this.handle = handle;
    }

    public Object getObject() {
        return object;
    }

    public Handle getHandle() {
        return handle;
    }

}
