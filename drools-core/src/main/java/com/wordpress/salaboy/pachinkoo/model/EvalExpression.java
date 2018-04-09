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
public class EvalExpression implements Expression {

    private final String condition;

    /**
     * @param condition
     */
    public EvalExpression(String condition) {
        super();
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "EvalExpression [condition=" + condition + "]";
    }

}
