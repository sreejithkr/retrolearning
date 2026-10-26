package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;


public class BinaryExpression implements Expression {
    protected Expression left;
    protected Expression right;
    protected TypeInfo type;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
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
        return type;
    }
}
