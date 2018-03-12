package com.wordpress.salaboy.pachinkoo;

/**
 * @author ezsalro
 *
 */
public class Cheese {

    public Cheese(String name) {
        super();
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cheese [name=" + name + "]";
    }

}
