package com.wordpress.salaboy.pachinkoo;


import java.util.ArrayList;
import java.util.List;


/**
 * @author ezsalro
 *
 */
public class Movie {

    private final String name;

    private List< String > actors = new ArrayList<>();


    public Movie( String name ) {

        super();
        this.name = name;
    }


    public Movie( String name, List< String > actors ) {

        super();
        this.name = name;
        this.actors.addAll( actors );
    }


    public String getName() {

        return name;
    }


    /**
     * @return the actors
     */
    public List< String > getActors() {

        return actors;
    }


    /**
     * @param actors
     *            the actors to set
     */
    public void setActors( List< String > actors ) {

        this.actors = actors;
    }

}
