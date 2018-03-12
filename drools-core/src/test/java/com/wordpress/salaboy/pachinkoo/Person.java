/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

/**
 *
 * @author salaboy
 */
public class Person {

    private String name;
    private final String favoriteCheese;

    public Person(String name) {
        this.name = name;
        this.favoriteCheese = null;
    }

    public Person(String name, String favoriteCheese) {
        this.name = name;
        this.favoriteCheese = favoriteCheese;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavoriteCheese() {
        return favoriteCheese;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", favoriteCheese=" + favoriteCheese + "]";
    }

}
