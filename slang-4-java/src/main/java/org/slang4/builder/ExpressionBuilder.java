package org.slang4.builder;

import org.slang4.RDParser;
import org.slang4.ast.Expression;

public class ExpressionBuilder extends AbstractBuilder {
    public String expressionString;

    ExpressionBuilder(String expression) {
        this.expressionString = expression;
    }

    public Expression build() {
        try {
            RDParser parser = new RDParser(expressionString);
            return parser.callExpression();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
