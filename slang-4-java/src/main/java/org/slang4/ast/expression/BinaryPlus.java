package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.RuntimeContext;
import org.slang4.SymbolInfo;
import org.slang4.TypeInfo;

public class BinaryPlus extends BinaryExpression {

    public BinaryPlus(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public SymbolInfo evaluate(RuntimeContext context) {
        SymbolInfo leftEval = this.left.evaluate(context);
        SymbolInfo rightEval = this.right.evaluate(context);

        SymbolInfo returnValue = new SymbolInfo();

        if (leftEval.type != rightEval.type){
            throw new RuntimeException("Incompatible types");
        } else {
            switch (leftEval.type){
                case TypeInfo.numeric:
                    returnValue.dblVal = leftEval.dblVal + rightEval.dblVal;
                    returnValue.type = TypeInfo.numeric;
                    break;
                case TypeInfo.string:
                    returnValue.strVal = leftEval.strVal + rightEval.strVal;
                    returnValue.type = TypeInfo.string;
                    break;
                default:
                    throw new RuntimeException("Unsupported types");

            }
        }


        return returnValue;
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        TypeInfo leftEval = this.left.typeCheck(context);
        TypeInfo rightEval = this.right.typeCheck(context);


        if (leftEval == rightEval && leftEval != TypeInfo.bool){
            type = leftEval;
        } else {
            throw new RuntimeException("Incompatible types");
        }
        return type;
    }

    @Override
    public TypeInfo getType() {
        return type;
    }
}
