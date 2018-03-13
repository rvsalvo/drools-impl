/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;


import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author salaboy
 */
public class WorkingMemoryImpl implements WorkingMemory {

    private final Agenda agenda = new AgendaImpl();

    private final Rete rete;

    private final Map< Handle, Object > assertStore;


    public WorkingMemoryImpl() {

        rete = new Rete( this );
        assertStore = new HashMap< Handle, Object >();
    }


    @Override
    public Agenda getAgenda() {

        return this.agenda;
    }


    @Override
    public int fireAllRules() {

        return this.agenda.fireAllRules();
    }


    @Override
    public Rete getRete() {

        return rete;
    }


    @Override
    public Handle insert( Object object ) {

        final Handle handle = createHandle( object );
        rete.assertObject( handle, new PropagationContext(), this );
        return handle;
    }


    private Handle createHandle( Object object ) {

        final FactHandle factHandle = new FactHandle( object );
        assertStore.put( factHandle, object );
        return factHandle;
    }


    public Object getObjectForFactHandle( Handle handle ) {

        return assertStore.get( handle );
    }


    @Override
    public Map< Handle, Object > getAssertedFacts() {

        return assertStore;
    }

}
