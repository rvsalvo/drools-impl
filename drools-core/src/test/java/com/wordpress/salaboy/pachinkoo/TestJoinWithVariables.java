package com.wordpress.salaboy.pachinkoo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wordpress.salaboy.pachinkoo.rhs.Action;

/**
 * 
 * @author ezsalro
 *
 */
public class TestJoinWithVariables {

    /**
     * rule test<br/>
     * when<br/>
     * &nbsp;Cheese( $chedddar : name == "cheddar" )<br/>
     * &nbsp;$person : Person( favouriteCheese == $cheddar )<br/>
     * then<br/>
     * &nbsp;System.out.println( $person.getName() + " likes cheddar" );<br/>
     * end<br/>
     */
    @Test
    public void joinNodeTest() {
        //Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        //Get the Root/Rete Node
        final Rete rete = wm.getRete();

        //Create one Object Type Node: Cheese()
        final ObjectTypeNode cheeseTypeNode = new ObjectTypeNode(Cheese.class.getCanonicalName());

        final AlphaNode alphaNode = new AlphaNode("$cheddar", COMPARATOR.EQUAL, "name", "cheddar");
        cheeseTypeNode.addObjectSink(alphaNode);

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink(leftInputAdapter);

        //final JoinNode joinNode = new JoinNode(new SingleValueRestrictionConstraint("Person.favouriteCheese", "Cheese.name", COMPARATOR.EQUAL));
        final JoinNode joinNode = new JoinNode(new SingleValueRestrictionConstraint(new FieldRestriction(Person.class, "favouriteCheese"),
                new FieldVariable("$cheddar"), COMPARATOR.EQUAL));

        final RuleTerminalNode terminalNode = new RuleTerminalNode("matches a person and execute an action", new Action() {

            @Override
            public void execute(Tuple tuple, PropagationContext context) {
                System.out.println("My Tuple = " + tuple);
            }
        });
        leftInputAdapter.addTupleSink(joinNode);

        //Add OTN Cheese() to the Network
        rete.addObjectSink(cheeseTypeNode);

        //Create one Object Type Node: Person()
        final ObjectTypeNode personTypeNode = new ObjectTypeNode(Person.class.getCanonicalName());

        personTypeNode.addObjectSink(joinNode);

        joinNode.addTupleSink(terminalNode);

        //Add OTN Person() to the Network
        rete.addObjectSink(personTypeNode);

        //Let's use the network

        wm.insert(new Person("Fulano", "cheddar"));
        //Nothing happens until here.. let's add another Fact
        assertEquals(0, wm.getAgenda().size());

        wm.insert(new Cheese("cheddar"));
        assertEquals(1, wm.getAgenda().size());

        final int fired = wm.fireAllRules();
        assertEquals(1, fired);

    }
}
