package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class StringLiteral implements Expression {
    private SymbolInfo symbolInfo;
    public StringLiteral(String value) {
        this.symbolInfo = new SymbolInfo();
        this.symbolInfo.type = TypeInfo.string;
        this.symbolInfo.strVal = value;
        this.symbolInfo.symbolName = null;
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
