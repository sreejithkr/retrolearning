package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class BooleanConstant implements Expression {
    private SymbolInfo symbolInfo;

    public BooleanConstant(Boolean  value) {
        this.symbolInfo = new SymbolInfo();
        this.symbolInfo.symbolName = null;
        this.symbolInfo.boolVal = value;
        this.symbolInfo.type = TypeInfo.bool;
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
