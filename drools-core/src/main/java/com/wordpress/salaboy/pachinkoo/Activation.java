/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;


import java.util.Collection;

import com.wordpress.salaboy.pachinkoo.rhs.Action;


/**
 *
 * @author salaboy
 */
public class Activation {

    private String ruleName;

    private Action action;

    private Collection< Handle > handles;


    public Activation( String ruleName, Action action, Collection< Handle > handles ) {

        this.ruleName = ruleName;
        this.action = action;
        this.handles = handles;
    }


    public Action getAction() {

        return action;
    }


    public String getRuleName() {

        return ruleName;
    }


    public Collection< Handle > getHandles() {

        return handles;
    }


    public void execute() {

        action.execute( ruleName, handles, null );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "Activation [ruleName=" + ruleName + ", action=" + action + ", handles=" + handles + "]";
    }

}
