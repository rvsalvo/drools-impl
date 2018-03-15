package com.wordpress.salaboy.pachinkoo;

/**
 * 
 * @author ezsalro
 *
 */
public class RestrictionResult {

    private final Object object;
    private final Handle handle;
    private final String variable;
    private final ClassVariable classVariable;

    public String getVariable() {
        return variable;
    }

    public ClassVariable getClassVariable() {
        return classVariable;
    }

    public RestrictionResult(Object object, Handle handle) {
        super();
        this.object = object;
        this.handle = handle;
        this.variable = null;
        this.classVariable = null;
    }

    public RestrictionResult(Object object, Handle handle, ClassVariable classVariable, String variable) {
        super();
        this.object = object;
        this.handle = handle;
        this.variable = variable;
        this.classVariable = classVariable;

    }

    public Object getObject() {
        return object;
    }

    public Handle getHandle() {
        return handle;
    }

}
