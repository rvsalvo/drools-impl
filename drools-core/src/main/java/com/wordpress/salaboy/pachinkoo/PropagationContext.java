/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public class PropagationContext {

    private final Map<String, Object> bindingVariables = new HashMap<>();

    private final Collection<Handle> propagatedHandles = new HashSet<>();

    public Collection<Handle> getPropagatedHandles() {
        return propagatedHandles;
    }

    public Map<String, Object> getBindingVariables() {
        return bindingVariables;
    }

}
