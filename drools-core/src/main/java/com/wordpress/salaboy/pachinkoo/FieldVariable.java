package com.wordpress.salaboy.pachinkoo;

/**
 * @author Rodrigo Salvo
 *
 */
public class FieldVariable {

    private final String variable;
    private final String method;

    public FieldVariable(String variable) {
        super();
        this.variable = variable;
        this.method = null;
    }

    public FieldVariable(String variable, String method) {
        super();
        this.variable = variable;
        this.method = method;
    }

    public String getVariable() {
        return variable;
    }

    public String getMethod() {
        return method;
    }

}
