package org.slang4.ast.statement;

import org.slang4.ExecutableGenerationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.ast.Statement;
import org.slang4.ast.expression.Expression;
import org.slang4.ast.expression.Variable;

public class AssignmentStatement implements Statement {
    Expression expr;
    private Variable variable;

    AssignmentStatement(Variable variable, Expression expr) {
        this.variable = variable;
        this.expr = expr;
    }

    public AssignmentStatement(SymbolInfo symbolInfo, Expression expr) {
        this.variable = new Variable(symbolInfo);
        this.expr = expr;
    }

    @Override
    public SymbolInfo execute(RuntimeContext context) {
        SymbolInfo val = expr.evaluate(context);
        context.getSymbolTable().assign(variable, val);
        return null;
    }

    @Override
    public Boolean compile(ExecutableGenerationContext context) {
        return null;
    }
}
