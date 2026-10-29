package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class NumericExpression implements Expression {

    private SymbolInfo symbolInfo;
    private double value;

    public NumericExpression(double value) {
        this.value = value;
        symbolInfo = new SymbolInfo();
        symbolInfo.type = TypeInfo.numeric;
        symbolInfo.dblVal = value;
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        return symbolInfo;//Should be value in the first step
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        return symbolInfo.type;
    }

    @Override
    public TypeInfo getType() {
        return symbolInfo.type;
    }
}
