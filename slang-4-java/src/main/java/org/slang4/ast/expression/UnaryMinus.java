package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class UnaryMinus extends UnaryExpression{
    public UnaryMinus(Expression expression) {
        super(expression);
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        SymbolInfo expression = this.expression.evaluate(context);
        if (expression.type == TypeInfo.numeric) {
            SymbolInfo returnVal = new SymbolInfo();
            returnVal.dblVal = -expression.dblVal;
            returnVal.type = TypeInfo.numeric;
            returnVal.symbolName = "";
            return returnVal;
        } else {
            throw new RuntimeException("type not supported");
        }
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        TypeInfo expressionTypeInfo = expression.typeCheck(context);

        if (expressionTypeInfo == TypeInfo.numeric){
            typeInfo = expressionTypeInfo;
        } else {
            throw new RuntimeException("Incompatible types");
        }
        return typeInfo;
    }
}
