/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import com.wordpress.salaboy.pachinkoo.util.RestrictionUtils;

/**
 *
 * @author salaboy
 */
public class JoinNode extends BetaNode {

    public JoinNode(BetaConstraints constraint) {
        super(constraint);
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assertLeftTuple(LeftTuple leftTuple, PropagationContext propagationContext, WorkingMemory wm) {
        getMemory().addLeftTuple(leftTuple);

        for (final RightTuple rightTuple : getMemory().getRightTupleMemory()) {
            if (constraint instanceof EmptyBetaConstraints) {
                System.out.println("Left Tuple = " + leftTuple);
                System.out.println("Right Tuple = " + rightTuple);
                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, propagationContext, wm);
                }
            } else if (constraint instanceof SingleValueRestrictionConstraint) {
                final SingleValueRestrictionConstraint singleConstraint = (SingleValueRestrictionConstraint) constraint;
                if (singleConstraint.getComparator() != null) {

                    final Object fieldValue = RestrictionUtils.getValueForConstraint(leftTuple, propagationContext, singleConstraint.getField());

                    final Object restrictionValue = RestrictionUtils.getValueForConstraint(rightTuple, propagationContext,
                            singleConstraint.getRestriction());

                    switch (singleConstraint.getComparator()) {
                        case EQUAL:
                            if (fieldValue.equals(restrictionValue)) {
                                for (final LeftTupleSink sink : sinks) {
                                    sink.assertLeftTuple(leftTuple, propagationContext, wm);
                                }
                            }
                            break;
                    }
                }

            }

        }
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {
        final RightTuple rightTuple = new RightTuple(factHandle, this);
        getMemory().addRightTuple(rightTuple); //?? this to the rightTupleSink????
        for (final LeftTuple leftTuple : getMemory().getLeftTupleMemory()) {
            if (constraint instanceof EmptyBetaConstraints) {
                System.out.println("Left Tuple = " + leftTuple);
                System.out.println("Right Tuple = " + rightTuple);
                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, propagationContext, wm);
                }
            } else if (constraint instanceof SingleValueRestrictionConstraint) {

                //            for (LeftTupleSink sink : sinks) {
                //                sink.assertLeftTuple(leftTuple, propagationContext, wm);
                //            }

            }

        }

    }
}
