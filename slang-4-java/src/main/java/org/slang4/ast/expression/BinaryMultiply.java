package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class BinaryMultiply extends BinaryExpression {
    public BinaryMultiply(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        SymbolInfo leftEval = this.left.evaluate(context);
        SymbolInfo rightEval = this.right.evaluate(context);
        SymbolInfo returnValue = new SymbolInfo();
        if (leftEval.type != rightEval.type ){
            throw new RuntimeException("Mismatching types");
        } else {
            if (leftEval.type == TypeInfo.numeric) {
                returnValue.dblVal = leftEval.dblVal * rightEval.dblVal;
                returnValue.type = TypeInfo.numeric;
            } else {
                throw new RuntimeException("Unsupported types");
            }
        }

        return returnValue;
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        TypeInfo leftEval = this.left.typeCheck(context);
        TypeInfo rightEval = this.right.typeCheck(context);

        if (leftEval == rightEval && leftEval == TypeInfo.numeric){
            type = leftEval;
        } else {
            throw new RuntimeException("Incompatible types");
        }
        return type;
    }
}
