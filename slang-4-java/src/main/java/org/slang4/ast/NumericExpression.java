package org.slang4.ast;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class NumericExpression implements Expression {

    private double value;

    public NumericExpression(double value) {
        this.value = value;
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        return null;//Should be value in the first step
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
