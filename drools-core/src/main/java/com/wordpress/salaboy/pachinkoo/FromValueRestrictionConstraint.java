package com.wordpress.salaboy.pachinkoo;

/**
 *
 * @author Rodrigo Salvo
 */
public class FromValueRestrictionConstraint implements BetaConstraints {

    private final Object field;
    private final Object restriction;
    private final COMPARATOR comparator;

    public FromValueRestrictionConstraint(Object field, Object restriction, COMPARATOR comparator) {
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
