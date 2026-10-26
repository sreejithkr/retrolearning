package org.slang4.ast.expression;

import org.slang4.*;

public class NumericConstant implements Expression {
    private SymbolInfo symbolInfo;

    NumericConstant(double value) {
        symbolInfo = new SymbolInfo();
        symbolInfo.dblVal = value;
        symbolInfo.symbolName = null;
        symbolInfo.type = TypeInfo.numeric;
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        return symbolInfo;
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
