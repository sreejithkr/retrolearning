package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class UnaryExpression implements Expression {
    protected Expression expression;
    protected TypeInfo typeInfo;

    public UnaryExpression(Expression expression) {
        this.expression = expression;
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
        return typeInfo;
    }
}
