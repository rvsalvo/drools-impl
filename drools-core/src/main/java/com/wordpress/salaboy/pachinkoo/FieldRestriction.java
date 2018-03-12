package com.wordpress.salaboy.pachinkoo;

/**
 * @author ezsalro
 *
 */
public class FieldRestriction {

    private Class<?> clazz;
    private String field;

    public FieldRestriction(Class<?> clazz, String field) {
        super();
        this.clazz = clazz;
        this.field = field;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
