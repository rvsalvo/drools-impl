package com.wordpress.salaboy.pachinkoo;


import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import com.wordpress.salaboy.pachinkoo.rhs.DefaultAction;


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

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode cheeseTypeNode = new ObjectTypeNode( Cheese.class.getCanonicalName() );

        final AlphaNode alphaNode = new AlphaNode( "$cheddar", COMPARATOR.EQUAL, "name", "cheddar" );
        cheeseTypeNode.addObjectSink( alphaNode );

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink( leftInputAdapter );

        final JoinNode joinNode =
            new JoinNode( new SingleValueRestrictionConstraint( new FieldRestriction( new ClassVariable( "$person" ), Person.class, "favoriteCheese" ),
                new FieldVariable( "$cheddar" ), COMPARATOR.EQUAL ) );

        final RuleTerminalNode terminalNode = new RuleTerminalNode( "matches a person and execute an action", new DefaultAction() {

            @Override
            public void execute( String rule, Collection< Handle > handles, PropagationContext context ) {

                System.out.println( getResult( handles, context.getBindingVariables(), "$person", "getName" ) + " likes cheese" );
            }
        } );
        leftInputAdapter.addTupleSink( joinNode );

        // Add OTN Cheese() to the Network
        rete.addObjectSink( cheeseTypeNode );

        // Create one Object Type Node: Person()
        final ObjectTypeNode personTypeNode = new ObjectTypeNode( Person.class.getCanonicalName() );

        personTypeNode.addObjectSink( joinNode );

        joinNode.addTupleSink( terminalNode );

        // Add OTN Person() to the Network
        rete.addObjectSink( personTypeNode );

        // Let's use the network

        wm.insert( new Person( "Fulano", "cheddar" ) );
        // Nothing happens until here.. let's add another Fact
        assertEquals( 0, wm.getAgenda().size() );

        wm.insert( new Cheese( "cheddar" ) );
        assertEquals( 1, wm.getAgenda().size() );

        final int fired = wm.fireAllRules();
        assertEquals( 1, fired );

    }


    /**
     * rule movie<br/>
     * when<br/>
     * &nbsp;Movie( $movieName : name == "Avengers" )<br/>
     * &nbsp;Movie( $movieName2: name == "Back to The future II" )<br/>
     * &nbsp;eval( $movieName2 != $movieName )<br/>
     * then<br/>
     * &nbsp;System.out.println( $movieName + " not equals " + $movieName2
     * );<br/>
     * end<br/>
     */
    @Test
    public void movieRuleTest() {

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode movieTypeNode = new ObjectTypeNode( Movie.class.getCanonicalName() );

        final AlphaNode alphaNode = new AlphaNode( "$movieName", COMPARATOR.EQUAL, "name", "Avengers" );
        movieTypeNode.addObjectSink( alphaNode );

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink( leftInputAdapter );

        final AlphaNode alphaNode2 = new AlphaNode( "$movieName2", COMPARATOR.EQUAL, "name", "Back to the Future II" );
        movieTypeNode.addObjectSink( alphaNode2 );

        final LeftInputAdapterNode leftInputAdapter2 = new LeftInputAdapterNode();
        alphaNode2.addObjectSink( leftInputAdapter2 );

        final JoinNode joinNode =
            new JoinNode( new SingleValueRestrictionConstraint( new FieldVariable( "$movieName" ), new FieldVariable( "$movieName2" ), COMPARATOR.NOT_EQUAL ) );

        final RuleTerminalNode terminalNode = new RuleTerminalNode( "rule movie", new DefaultAction() {

            @Override
            public void execute( String rule, Collection< Handle > handles, PropagationContext context ) {

                System.out.println(
                    getResult( context.getBindingVariables(), "$movieName" ) + " not equals " + getResult( context.getBindingVariables(), "$movieName2" ) );
            }
        } );
        leftInputAdapter.addTupleSink( joinNode );

        leftInputAdapter2.addTupleSink( joinNode );

        joinNode.addTupleSink( terminalNode );

        // Add OTN Cheese() to the Network
        rete.addObjectSink( movieTypeNode );

        wm.insert( new Movie( "Avengers" ) );
        // Nothing happens until here.. let's add another Fact
        assertEquals( 0, wm.getAgenda().size() );

        wm.insert( new Movie( "Back to the Future II" ) );

        wm.fireAllRules();

    }


    /**
     * rule rule01<br/>
     * when<br/>
     * &nbsp;$movie:Movie( name == 'Avengers' )<br/>
     * then<br/>
     * &nbsp;System.out.println($movie.getName());<br/>
     * end<br/>
     */
    @Test
    public void testRepeatedInformation() {

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode movieTypeNode = new ObjectTypeNode( Movie.class.getCanonicalName() );

        final AlphaNode alphaNode = new AlphaNode( new ClassVariable( "$movie" ), COMPARATOR.EQUAL, "name", "Avengers" );
        movieTypeNode.addObjectSink( alphaNode );

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        alphaNode.addObjectSink( leftInputAdapter );

        final RuleTerminalNode terminalNode = new RuleTerminalNode( "rule01", new DefaultAction() {

            @Override
            public void execute( String rule, Collection< Handle > handles, PropagationContext context ) {

                System.out.println( getResult( handles, context.getBindingVariables(), "$movie", "getName" ) );
            }
        } );
        leftInputAdapter.addTupleSink( terminalNode );

        // Add OTN Cheese() to the Network
        rete.addObjectSink( movieTypeNode );

        wm.insert( new Movie( "Back to the Future" ) );
        // Nothing happens until here.. let's add another Fact
        assertEquals( 0, wm.getAgenda().size() );

        wm.insert( new Movie( "Avengers" ) );
        assertEquals( 1, wm.getAgenda().size() );

        wm.insert( new Movie( "Avengers" ) );

        final int fired = wm.fireAllRules();
        assertEquals( 2, fired );

    }


    @Test
    public void testRepeatedNoAlphaNodeInformation() {

        // Create Working Memory
        final WorkingMemory wm = new WorkingMemoryImpl();
        // Get the Root/Rete Node
        final Rete rete = wm.getRete();

        // Create one Object Type Node: Cheese()
        final ObjectTypeNode movieTypeNode = new ObjectTypeNode( new ClassVariable( "$movie" ), Movie.class.getCanonicalName() );

        final LeftInputAdapterNode leftInputAdapter = new LeftInputAdapterNode();
        movieTypeNode.addObjectSink( leftInputAdapter );

        final RuleTerminalNode terminalNode = new RuleTerminalNode( "rule no alpha node", new DefaultAction() {

            @Override
            public void execute( String rule, Collection< Handle > handles, PropagationContext context ) {

                System.out.println( getResult( handles, context.getBindingVariables(), "$movie", "getName" ) );
            }
        } );
        leftInputAdapter.addTupleSink( terminalNode );

        // Add OTN Cheese() to the Network
        rete.addObjectSink( movieTypeNode );

        wm.insert( new Movie( "Back to the Future II" ) );
        // Nothing happens until here.. let's add another Fact
        assertEquals( 1, wm.getAgenda().size() );

        wm.insert( new Movie( "Back to the Future III" ) );
        assertEquals( 2, wm.getAgenda().size() );

        final int fired = wm.fireAllRules();
        assertEquals( 2, fired );

    }
}
