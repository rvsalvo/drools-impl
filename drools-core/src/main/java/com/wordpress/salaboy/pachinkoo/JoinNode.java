/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        leftTuple.getBindVariables().putAll(propagationContext.getBindingVariables());
        getMemory().addLeftTuple(leftTuple);

        for (final RightTuple rightTuple : getMemory().getRightTupleMemory()) {

            boolean shouldPropagate = false;

            RestrictionResult leftResult = null;
            RestrictionResult rightResult = null;

            final Map<String, Object> bindingVariables = new HashMap<>();
            bindingVariables.putAll(propagationContext.getBindingVariables());
            bindingVariables.putAll(rightTuple.getBindVariables());

            if (constraint instanceof EmptyBetaConstraints) {
                shouldPropagate = true;
            } else if (constraint instanceof SingleValueRestrictionConstraint) {
                try {

                    final List<Handle> handles = new ArrayList<>();
                    handles.addAll(leftTuple.getFactHandles());
                    handles.addAll(rightTuple.getFactHandles());

                    leftResult = RestrictionUtils.getValueForConstraint(handles, bindingVariables, constraint.getField());
                    final Object fieldValue = leftResult.getObject();

                    rightResult = RestrictionUtils.getValueForConstraint(handles, bindingVariables, constraint.getRestriction());
                    final Object restrictionValue = rightResult.getObject();

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
            if (shouldPropagate) {

                final PropagationContext context = new PropagationContext(bindingVariables);

                leftTuple = new LeftTuple(leftTuple.getFactHandles(), leftTuple.getSink(), leftTuple.getBindVariables());
                populateResults(leftTuple, context, rightResult);
                populateResults(leftTuple, context, leftResult);

                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, context, wm);
                }
            }

        }

    }

    private void populateResults(Tuple leftTuple, PropagationContext propagationContext, RestrictionResult restrictionResult) {

        if (restrictionResult != null) {
            if (restrictionResult.getVariable() != null) {
                propagationContext.getBindingVariables().put(restrictionResult.getVariable(), restrictionResult.getObject());
            }
            if (restrictionResult.getClassVariable() != null && restrictionResult.getHandle() != null) {
                propagationContext.getBindingVariables().put(restrictionResult.getClassVariable().getName(),
                        restrictionResult.getHandle().getObject());
            }
            if (restrictionResult.getHandle() != null && !leftTuple.getFactHandles().contains(restrictionResult.getHandle())) {
                leftTuple.getFactHandles().add(restrictionResult.getHandle());
            }
        }
    }

    @Override
    public void assertObject(Handle factHandle, PropagationContext propagationContext, WorkingMemory wm) {

        final RightTuple rightTuple = new RightTuple(factHandle, this, propagationContext.getBindingVariables());
        getMemory().addRightTuple(rightTuple);
        for (LeftTuple leftTuple : getMemory().getLeftTupleMemory()) {

            boolean shouldPropagate = false;

            RestrictionResult leftResult = null;
            RestrictionResult rightResult = null;

            final Map<String, Object> bindingVariables = new HashMap<>();
            bindingVariables.putAll(propagationContext.getBindingVariables());
            bindingVariables.putAll(leftTuple.getBindVariables());

            if (constraint instanceof EmptyBetaConstraints) {
                shouldPropagate = true;
            } else if (constraint instanceof SingleValueRestrictionConstraint) {

                final List<Handle> handles = new ArrayList<>();
                handles.addAll(leftTuple.getFactHandles());
                handles.addAll(rightTuple.getFactHandles());

                try {

                    leftResult = RestrictionUtils.getValueForConstraint(handles, bindingVariables, constraint.getField());
                    final Object fieldValue = leftResult.getObject();

                    rightResult = RestrictionUtils.getValueForConstraint(handles, bindingVariables, constraint.getRestriction());
                    final Object restrictionValue = rightResult.getObject();

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

            if (shouldPropagate) {
                leftTuple = new LeftTuple(leftTuple.getFactHandles(), leftTuple.getSink(), leftTuple.getBindVariables());
                leftTuple.getFactHandles().add(factHandle);
                final PropagationContext context = new PropagationContext(bindingVariables);
                for (final LeftTupleSink sink : sinks) {
                    sink.assertLeftTuple(leftTuple, context, wm);
                }
            }

        }

    }
}
