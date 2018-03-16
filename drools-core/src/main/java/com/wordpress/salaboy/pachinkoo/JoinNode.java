/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;


import java.util.Objects;

import com.wordpress.salaboy.pachinkoo.util.RestrictionUtils;


/**
 *
 * @author salaboy
 */
public class JoinNode extends BetaNode {

    public JoinNode( BetaConstraints constraint ) {

        super( constraint );
    }


    @Override
    public long getId() {

        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public void assertLeftTuple( LeftTuple leftTuple, PropagationContext propagationContext, WorkingMemory wm ) {

        leftTuple.getBindVariables().putAll( propagationContext.getBindingVariables() );
        getMemory().addLeftTuple( leftTuple );

        for ( final RightTuple rightTuple : getMemory().getRightTupleMemory() ) {

            boolean shouldPropagate = false;

            RestrictionResult leftResult = null;
            RestrictionResult rightResult = null;

            if ( constraint instanceof EmptyBetaConstraints ) {
                shouldPropagate = true;
            } else if ( constraint instanceof SingleValueRestrictionConstraint ) {
                try {

                    leftResult = RestrictionUtils.getValueForConstraint( leftTuple, propagationContext, constraint.getField() );
                    final Object fieldValue = leftResult.getObject();

                    rightResult = RestrictionUtils.getValueForConstraint( rightTuple, propagationContext, constraint.getRestriction() );
                    final Object restrictionValue = rightResult.getObject();

                    switch ( constraint.getComparator() ) {
                        case EQUAL:
                            shouldPropagate = Objects.equals( fieldValue, restrictionValue );
                            break;
                        case NOT_EQUAL:
                            shouldPropagate = !Objects.equals( fieldValue, restrictionValue );
                            break;
                    }

                } catch ( final Exception e ) {
                    e.printStackTrace();
                }
                if ( !shouldPropagate ) {
                    try {
                        rightResult = RestrictionUtils.getValueForConstraint( rightTuple, propagationContext, constraint.getField() );
                        final Object fieldValue = rightResult.getObject();
                        leftResult = RestrictionUtils.getValueForConstraint( leftTuple, propagationContext, constraint.getRestriction() );
                        final Object restrictionValue = leftResult.getObject();

                        switch ( constraint.getComparator() ) {
                            case EQUAL:
                                shouldPropagate = Objects.equals( fieldValue, restrictionValue );
                                break;
                            case NOT_EQUAL:
                                shouldPropagate = !Objects.equals( fieldValue, restrictionValue );
                                break;
                        }

                    } catch ( final Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
            if ( shouldPropagate ) {

                leftTuple = new LeftTuple( leftTuple.getFactHandles(), leftTuple.getSink(), leftTuple.getBindVariables() );
                populateResults( leftTuple, propagationContext, rightResult );
                populateResults( leftTuple, propagationContext, leftResult );

                for ( final LeftTupleSink sink : sinks ) {
                    sink.assertLeftTuple( leftTuple, propagationContext, wm );
                }
            }

        }

    }


    private void populateResults( LeftTuple leftTuple, PropagationContext propagationContext, RestrictionResult restrictionResult ) {

        if ( restrictionResult != null ) {
            if ( restrictionResult.getVariable() != null ) {
                propagationContext.getBindingVariables().put( restrictionResult.getVariable(), restrictionResult.getObject() );
            }
            if ( restrictionResult.getClassVariable() != null && restrictionResult.getHandle() != null ) {
                propagationContext.getBindingVariables().put( restrictionResult.getClassVariable().getName(), restrictionResult.getHandle().getObject() );
            }
            if ( restrictionResult.getHandle() != null && !leftTuple.getFactHandles().contains( restrictionResult.getHandle() ) ) {
                leftTuple.getFactHandles().add( restrictionResult.getHandle() );
            }
        }
    }


    @Override
    public void assertObject( Handle factHandle, PropagationContext propagationContext, WorkingMemory wm ) {

        final RightTuple rightTuple = new RightTuple( factHandle, this, propagationContext.getBindingVariables() );
        getMemory().addRightTuple( rightTuple );
        for ( LeftTuple leftTuple : getMemory().getLeftTupleMemory() ) {
            if ( constraint instanceof EmptyBetaConstraints ) {
                leftTuple = new LeftTuple( leftTuple.getFactHandles(), leftTuple.getSink(), leftTuple.getBindVariables() );
                leftTuple.getFactHandles().add( factHandle );
                for ( final LeftTupleSink sink : sinks ) {
                    sink.assertLeftTuple( leftTuple, propagationContext, wm );
                }
            } else if ( constraint instanceof SingleValueRestrictionConstraint ) {

                // for (LeftTupleSink sink : sinks) {
                // sink.assertLeftTuple(leftTuple, propagationContext, wm);
                // }

            }

        }

    }
}
