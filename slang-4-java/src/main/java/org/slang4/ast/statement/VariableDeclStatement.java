package org.slang4.ast.statement;

import org.slang4.ExecutableGenerationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.ast.Statement;
import org.slang4.ast.expression.Expression;
import org.slang4.ast.expression.Variable;

public class VariableDeclStatement implements Statement {
    private Variable variable;
    private SymbolInfo symbolInfo;

    public VariableDeclStatement(SymbolInfo info) {
        this.symbolInfo = info;
    }

    @Override
    public SymbolInfo execute(RuntimeContext context) {
        context.getSymbolTable().add(symbolInfo);
        variable = new Variable(symbolInfo);
        return null;
    }

    @Override
    public Boolean compile(ExecutableGenerationContext context) {
        return null;
    }
}
