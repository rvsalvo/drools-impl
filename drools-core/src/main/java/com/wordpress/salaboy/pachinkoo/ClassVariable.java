package com.wordpress.salaboy.pachinkoo;

/**
 * @author ezsalro
 *
 */
public class ClassVariable {

    private final String name;

    public ClassVariable(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassVariable [name=" + name + "]";
    }

}
