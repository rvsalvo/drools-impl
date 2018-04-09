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
public class ClassExpression implements Expression {

    private final String type;

    public String getType() {
        return type;
    }

    private final String condition;

    /**
     * @param condition
     */
    public ClassExpression(String type, String condition) {
        super();
        this.condition = condition;
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "ClassExpression [type=" + type + ", condition=" + condition + "]";
    }

}
