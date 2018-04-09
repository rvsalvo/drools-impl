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
package com.wordpress.salaboy.pachinkoo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.wordpress.salaboy.pachinkoo.model.ClassExpression;
import com.wordpress.salaboy.pachinkoo.model.EvalExpression;
import com.wordpress.salaboy.pachinkoo.model.Expression;
import com.wordpress.salaboy.pachinkoo.model.FromExpression;

/**
 * @author ezsalro
 *
 */
public class TestParser {

    private static final Pattern CLASS_PATTERN = Pattern.compile("^(\\w+)\\((.+)\\)$");
    private static final Pattern FROM_PATTERN = Pattern.compile("^(\\w+)\\((.+)\\)\\s*(from|FROM)\\s*(.+)$");

    public void test() {

        String text = "Element( name == \"Amazing\" )".trim();

        findClassPattern(text);

        System.out.println("\nFinding FROM pattern:");

        text = "Element( name == true ) from $variable".trim();

        findFromPattern(text);

        System.out.println("\nFinding EVAL pattern:");

        text = "eval( $name != '10' )".trim();

        findEvalPattern(text);
    }

    @Test
    public void testExpressionBuilder() {

        final String[] texts = new String[] { "Movie( $actors: actors, name =='Avengers', type == $type)", "Element( name == \"Amazing\" )",
                "Element( name == true ) from $variable", "eval( $name != '10' )" };
        final List<Expression> list = expressionPatternBuilder(texts);
        for (final Expression exp : list) {
            System.out.println(exp);
        }

    }

    private List<Expression> expressionPatternBuilder(final String... texts) {

        final List<Expression> expressions = new ArrayList<>();

        for (final String text : texts) {

            Matcher matcher = CLASS_PATTERN.matcher(text);

            if (matcher.find(0)) {
                final String g1 = matcher.group(1).trim();
                final String g2 = matcher.group(2);

                Expression expression = null;

                if ("eval".equals(g1)) {
                    expression = new EvalExpression(g2);
                } else {
                    expression = new ClassExpression(g1, g2);
                }
                expressions.add(expression);
            } else {
                matcher = FROM_PATTERN.matcher(text);
                if (matcher.find(0)) {
                    final String g1 = matcher.group(1);
                    final String g2 = matcher.group(2);
                    final String g4 = matcher.group(4);

                    final Expression expression = new FromExpression(g1, g2, g4);
                    expressions.add(expression);
                }
            }

        }

        return expressions;
    }

    private void findClassPattern(final String text) {
        final Matcher matcher = CLASS_PATTERN.matcher(text);

        if (matcher.find(0)) {
            final String g1 = matcher.group(1);
            final String g2 = matcher.group(2);

            System.out.println("g1=" + g1 + "\ng2=" + g2);
        }
    }

    private void findFromPattern(final String text) {
        final Matcher matcher = FROM_PATTERN.matcher(text);

        if (matcher.find(0)) {
            final String g1 = matcher.group(1);
            final String g2 = matcher.group(2);
            final String g4 = matcher.group(4);

            System.out.println("g1=" + g1 + "\ng2=" + g2 + "\ng4=" + g4);
        }
    }

    private void findEvalPattern(final String text) {
        final Matcher matcher = CLASS_PATTERN.matcher(text);

        if (matcher.find(0)) {
            final String g1 = matcher.group(1);
            final String g2 = matcher.group(2);
            System.out.println("g1=" + g1 + "\ng2=" + g2);
        }
    }

}
