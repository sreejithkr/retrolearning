package org.slang4.ast;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;


public class BinaryExpression implements Expression {
    private Expression left, right;
    private TypeInfo operator;

    public BinaryExpression(Expression left, Expression right, TypeInfo operator) {
        this.left = left;
        this.right = right;
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
