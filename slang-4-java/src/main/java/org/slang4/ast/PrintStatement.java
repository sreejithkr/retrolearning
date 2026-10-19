package org.slang4.ast;

import org.slang4.ExecutableGenerationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;

public class PrintStatement implements Statement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }
    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
       SymbolInfo symbolInfo = expression.evaluate(context);
       System.out.println(symbolInfo.toString());
       return null;
    }

    @Override
    public Boolean compile(ExecutableGenerationContext context) {
        return null;
    }
}
