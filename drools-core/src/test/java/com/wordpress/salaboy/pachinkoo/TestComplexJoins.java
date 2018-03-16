package com.wordpress.salaboy.pachinkoo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import com.wordpress.salaboy.pachinkoo.rhs.DefaultAction;

/**
 * @author ezsalro
 *
 */
public class TestComplexJoins {

    /**
     * rule movie<br/>
     * when<br/>
     * &nbsp;Movie( $movieName : name == "Avengers" )<br/>
     * &nbsp;Movie( $movieName2: name == "Back to The future II" )<br/>
     * &nbsp;eval( $movieName2 != $movieName )<br/>
     * then<br/>
     * &nbsp;System.out.println( $movieName + " not equals " + $movieName2 );<br/>
     * end<br/>
     */
    @Test
    public void movieRuleTest() {

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode movieTypeNode = new ObjectTypeNode(Movie.class.getCanonicalName());

        final AlphaNode alphaNode = new AlphaNode("$movieName", COMPARATOR.EQUAL, "name", "Avengers");
        movieTypeNode.addObjectSink(alphaNode);

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink(leftInputAdapter);

        final AlphaNode alphaNode2 = new AlphaNode("$movieName2", COMPARATOR.EQUAL, "name", "Back to the Future II");
        movieTypeNode.addObjectSink(alphaNode2);

        final JoinNode joinNode = new JoinNode(
                new SingleValueRestrictionConstraint(new FieldVariable("$movieName"), new FieldVariable("$movieName2"), COMPARATOR.NOT_EQUAL));

        final RuleTerminalNode terminalNode = new RuleTerminalNode("rule movie", new DefaultAction() {

            @Override
            public void execute(String rule, Collection<Handle> handles, PropagationContext context) {

                System.out.println(getResult(context.getBindingVariables(), "$movieName") + " not equals "
                        + getResult(context.getBindingVariables(), "$movieName2"));
            }
        });
        leftInputAdapter.addTupleSink(joinNode);

        alphaNode2.addObjectSink(joinNode);

        joinNode.addTupleSink(terminalNode);

        // Add OTN Cheese() to the Network
        rete.addObjectSink(movieTypeNode);

        wm.insert(new Movie("Avengers"));
        // Nothing happens until here.. let's add another Fact
        assertEquals(0, wm.getAgenda().size());

        wm.insert(new Movie("Back to the Future II"));

        wm.fireAllRules();

    }

    /**
     * rule test<br/>
     * when<br/>
     * &nbsp;Cheese( $chedddar : name == "cheddar" )<br/>
     * &nbsp;$person : Person( favouriteCheese == $cheddar )<br/>
     * then<br/>
     * &nbsp;System.out.println( $person.getName() + " likes cheddar" );<br/>
     * end<br/>
     * rule test2<br/>
     * when<br/>
     * &nbsp;$cheese: Cheese()<br/>
     * &nbsp;$person2 : Person( favouriteCheese != $cheese.getName() )<br/>
     * then<br/>
     * &nbsp;System.out.println( $person2.getName() + " does not like " + $cheese.getName() );<br/>
     * end<br/>
     * *
     */
    @Test
    public void joinNodeTest() {

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode cheeseTypeNode = new ObjectTypeNode(Cheese.class.getCanonicalName());

        final AlphaNode alphaNode = new AlphaNode("$cheddar", COMPARATOR.EQUAL, "name", "cheddar");
        cheeseTypeNode.addObjectSink(alphaNode);

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink(leftInputAdapter);

        final JoinNode joinNode = new JoinNode(new SingleValueRestrictionConstraint(
                new FieldRestriction(new ClassVariable("$person"), Person.class, "favoriteCheese"), new FieldVariable("$cheddar"), COMPARATOR.EQUAL));

        final RuleTerminalNode terminalNode = new RuleTerminalNode("rule test", new DefaultAction() {

            @Override
            public void execute(String rule, Collection<Handle> handles, PropagationContext context) {

                System.out.println(getResult(handles, context.getBindingVariables(), "$person", "getName") + " likes cheese");
            }
        });
        leftInputAdapter.addTupleSink(joinNode);

        // Add OTN Cheese() to the Network
        rete.addObjectSink(cheeseTypeNode);

        // Create one Object Type Node: Person()
        final ObjectTypeNode personTypeNode = new ObjectTypeNode(Person.class.getCanonicalName());

        personTypeNode.addObjectSink(joinNode);

        joinNode.addTupleSink(terminalNode);

        // Add OTN Person() to the Network
        rete.addObjectSink(personTypeNode);

        final EmptyAlphaNode emptyAlphaNode = new EmptyAlphaNode(new ClassVariable("$cheese"));
        cheeseTypeNode.addObjectSink(emptyAlphaNode);

        final LeftInputAdapterNode leftInputAdapter2 = new LeftInputAdapterNode();
        emptyAlphaNode.addObjectSink(leftInputAdapter2);

        final JoinNode joinNode2 = new JoinNode(
                new SingleValueRestrictionConstraint(new FieldRestriction(new ClassVariable("$person2"), Person.class, "favoriteCheese"),
                        new FieldVariable("$cheese", "getName"), COMPARATOR.NOT_EQUAL));

        final RuleTerminalNode terminalNode2 = new RuleTerminalNode("rule test2", new DefaultAction() {

            @Override
            public void execute(String rule, Collection<Handle> handles, PropagationContext context) {

                System.out.println(getResult(handles, context.getBindingVariables(), "$person2", "getName") + " does not like "
                        + getResult(handles, context.getBindingVariables(), "$cheese", "getName"));
            }
        });
        leftInputAdapter2.addTupleSink(joinNode2);

        personTypeNode.addObjectSink(joinNode2);

        joinNode2.addTupleSink(terminalNode2);

        // Let's use the network
        wm.insert(new Person("Fulano", "cheddar"));
        // Nothing happens until here.. let's add another Fact
        assertEquals(0, wm.getAgenda().size());

        wm.insert(new Cheese("parmesan"));

        final int fired = wm.fireAllRules();
        assertEquals(1, fired);

    }

}
