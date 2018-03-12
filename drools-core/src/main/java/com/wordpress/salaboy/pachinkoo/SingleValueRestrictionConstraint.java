/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.salaboy.pachinkoo;

/**
 *
 * @author salaboy
 */
public class SingleValueRestrictionConstraint implements BetaConstraints {

    private final Object field;
    private final Object restriction;
    private final COMPARATOR comparator;

    public SingleValueRestrictionConstraint(Object field, Object restriction, COMPARATOR comparator) {
        this.field = field;
        this.restriction = restriction;
        this.comparator = comparator;
    }

    @Override
    public Object getField() {
        return field;
    }

    @Override
    public Object getRestriction() {
        return restriction;
    }

    @Override
    public COMPARATOR getComparator() {
        return comparator;
    }

}
