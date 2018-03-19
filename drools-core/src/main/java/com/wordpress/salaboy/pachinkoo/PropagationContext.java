/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;


import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Rodrigo Salvo
 */
public class PropagationContext {

    private final Map< String, Object > bindingVariables = new HashMap<>();


    public PropagationContext() {

        super();
    }


    public PropagationContext( Map< String, Object > bindingVariables ) {

        super();
        this.bindingVariables.putAll( bindingVariables );
    }


    public Map< String, Object > getBindingVariables() {

        return bindingVariables;
    }

}
