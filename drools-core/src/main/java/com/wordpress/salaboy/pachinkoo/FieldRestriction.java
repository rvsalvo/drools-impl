package com.wordpress.salaboy.pachinkoo;

/**
 * @author ezsalro
 *
 */
public class FieldRestriction {

    private String variable;
    private Class<?> type;

    private String field;
    private ClassVariable classVariable;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public ClassVariable getClassVariable() {
        return classVariable;
    }

    public void setClassVariable(ClassVariable classVariable) {
        this.classVariable = classVariable;
    }

    public FieldRestriction(ClassVariable classVariable, Class<?> type, String field) {

        super();
        this.classVariable = classVariable;
        this.type = type;
        this.field = field;
    }

    public FieldRestriction(String variable, Class<?> type, String field) {

        super();
        this.type = type;
        this.field = field;
        this.variable = variable;
    }

    public FieldRestriction(Class<?> type, String field) {

        super();
        this.type = type;
        this.field = field;
    }

    public String getField() {

        return field;
    }

    public void setField(String field) {

        this.field = field;
    }

    /**
     * @return the type
     */
    public Class<?> getType() {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Class<?> type) {

        this.type = type;
    }

}
