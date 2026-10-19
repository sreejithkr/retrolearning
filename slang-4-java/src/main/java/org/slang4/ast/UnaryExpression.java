package org.slang4.ast;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class UnaryExpression implements Expression {
    private Expression expression;
    private TypeInfo operator;

    public UnaryExpression(Expression expression, TypeInfo operator) {
        this.expression = expression;
        this.operator = operator;
    }
    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        return null;
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        return null;
    }

    @Override
    public TypeInfo getType() {
        return null;
    }
}
