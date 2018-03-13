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

            boolean shouldPropagate = false;

            if (constraint instanceof EmptyBetaConstraints) {
                shouldPropagate = true;
            } else if (constraint instanceof SingleValueRestrictionConstraint) {
                Object fieldValue = null;
                Object restrictionValue = null;
                try {
                    fieldValue = RestrictionUtils.getValueForConstraint(leftTuple, propagationContext, constraint.getField());
                    restrictionValue = RestrictionUtils.getValueForConstraint(rightTuple, propagationContext, constraint.getRestriction());

                    switch (constraint.getComparator()) {
                        case EQUAL:
                            shouldPropagate = Objects.equals(fieldValue, restrictionValue);
                            break;
                        case NOT_EQUAL:
                            shouldPropagate = !Objects.equals(fieldValue, restrictionValue);
                            break;
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                }
                if (!shouldPropagate) {
                    try {
                        fieldValue = RestrictionUtils.getValueForConstraint(rightTuple, propagationContext, constraint.getField());
                        restrictionValue = RestrictionUtils.getValueForConstraint(leftTuple, propagationContext, constraint.getRestriction());

                        switch (constraint.getComparator()) {
                            case EQUAL:
                                shouldPropagate = Objects.equals(fieldValue, restrictionValue);
                                break;
                            case NOT_EQUAL:
                                shouldPropagate = !Objects.equals(fieldValue, restrictionValue);
                                break;
                        }

                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (shouldPropagate) {
                propagationContext.getPropagatedHandles().addAll(leftTuple.getFactHandles());

                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, propagationContext, wm);
                }
            }

        }

    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {

        final RightTuple rightTuple = new RightTuple(factHandle, this);
        getMemory().addRightTuple(rightTuple); // ?? this to the
                                               // rightTupleSink????
        for (final LeftTuple leftTuple : getMemory().getLeftTupleMemory()) {
            if (constraint instanceof EmptyBetaConstraints) {
                System.out.println("Left Tuple = " + leftTuple);
                System.out.println("Right Tuple = " + rightTuple);
                propagationContext.getPropagatedHandles().addAll(leftTuple.getFactHandles());
                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, propagationContext, wm);
                }
            } else if (constraint instanceof SingleValueRestrictionConstraint) {

                // for (LeftTupleSink sink : sinks) {
                // sink.assertLeftTuple(leftTuple, propagationContext, wm);
                // }

            }

        }

    }
}
