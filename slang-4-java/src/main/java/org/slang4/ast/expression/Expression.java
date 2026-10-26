package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public interface Expression {
    public SymbolInfo evaluate(RuntimeContext context);
    public TypeInfo typeCheck(CompilationContext context);
    public TypeInfo getType();


}
