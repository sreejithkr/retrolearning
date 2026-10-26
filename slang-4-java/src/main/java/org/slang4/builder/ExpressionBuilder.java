package org.slang4.builder;

import org.slang4.RDParser;
import org.slang4.ast.expression.Expression;

public class ExpressionBuilder extends AbstractBuilder {
    public String expressionString;

    ExpressionBuilder(String expression) {
        this.expressionString = expression;
    }

//    public Expression build() {
//        try {
//            RDParser parser = new RDParser(expressionString);
//            return parser.parse();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
