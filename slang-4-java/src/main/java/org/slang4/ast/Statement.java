package org.slang4.ast;

import org.slang4.ExecutableGenerationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;

public interface Statement {
    public SymbolInfo evaluate(RuntimeContext context);
    public abstract Boolean compile(ExecutableGenerationContext context);

}
