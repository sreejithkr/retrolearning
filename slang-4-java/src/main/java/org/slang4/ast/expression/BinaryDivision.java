package org.slang4.ast.expression;

import org.slang4.CompilationContext;
import org.slang4.TypeInfo;

public class BinaryDivision extends BinaryExpression{
    public BinaryDivision(Expression left, Expression right) {
        super(left, right);
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
