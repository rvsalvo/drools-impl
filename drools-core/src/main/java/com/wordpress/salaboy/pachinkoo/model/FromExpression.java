/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.wordpress.salaboy.pachinkoo.model;

/**
 * @author ezsalro
 *
 */
public class FromExpression implements Expression {

    private final String type;

    public String getType() {
        return type;
    }

    private final String condition;
    private final String fromCondition;

    /**
     * @param condition
     */
    public FromExpression(String type, String condition, String fromCondition) {
        super();
        this.type = type;
        this.condition = condition;
        this.fromCondition = fromCondition;
    }

    public String getCondition() {
        return condition;
    }

    public String getFromCondition() {
        return fromCondition;
    }

    @Override
    public String toString() {
        return "FromExpression [type=" + type + ", condition=" + condition + ", fromCondition=" + fromCondition + "]";
    }

}
