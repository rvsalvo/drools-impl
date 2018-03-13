/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo.rhs;


import java.util.Collection;

import com.wordpress.salaboy.pachinkoo.Handle;
import com.wordpress.salaboy.pachinkoo.PropagationContext;


/**
 *
 * @author salaboy
 */
public interface Action {

    void execute( String rule, Collection< Handle > handles, PropagationContext context );
}
