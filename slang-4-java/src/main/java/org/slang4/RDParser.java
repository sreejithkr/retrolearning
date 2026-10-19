package org.slang4;

import org.slang4.ast.BinaryExpression;
import org.slang4.ast.Expression;
import org.slang4.ast.NumericExpression;
import org.slang4.ast.UnaryExpression;

public class RDParser extends Lexer {
    Tokens currentToken;
    public RDParser(String expression) {
        super(expression);
    }

    public Expression callExpression(){
        currentToken = getToken();
        return expresion();
    }

    /*
    <Expr> ::= <Term> | Term { + | - } <Expr>
    <Term> ::= <Factor> | <Factor> {*|/} <Term>
    <Factor>::= <number> | ( <expr> ) | {+|-} <factor>
    * */
    public  Expression expresion(){
        Tokens lastToken = getToken();
        Expression returnValue = term();
        while (currentToken == Tokens.TOKEN_PLUS || currentToken == Tokens.TOKEN_MINUS){
            lastToken = currentToken;
            currentToken = getToken();
            Expression el   = expresion();
            returnValue = new BinaryExpression(returnValue,el, lastToken == Tokens.TOKEN_PLUS ? null: null);
        }
        return returnValue;
    }

    public  Expression term(){
        Tokens lastToken = getToken();
        Expression returnValue = factor();
        while (currentToken == Tokens.TOKEN_DIV || currentToken == Tokens.TOKEN_MUL){
            lastToken = currentToken;
            currentToken = getToken();
            Expression el   = term();
            returnValue = new BinaryExpression(returnValue, el, lastToken == Tokens.TOKEN_MUL ? null: null);
        }
        return returnValue;
    }
//    <Factor>::= <number> | ( <expr> ) | {+|-} <factor>
    public Expression factor(){
        Tokens lastToken = getToken();
        Expression returnValue = null;
        if(lastToken == Tokens.TOKEN_OPAREN){
            lastToken = currentToken;
            returnValue   = expresion();
            if (lastToken != Tokens.TOKEN_CPAREN){
                System.out.println("Missing Closing Parenthesis\n");
                throw new RuntimeException("Missing Closing Parenthesis");
            }
            currentToken = getToken();

        } else if (lastToken == Tokens.TOKEN_NUMERIC){
            returnValue = new NumericExpression(number);
        } else if (lastToken == Tokens.TOKEN_PLUS || lastToken == Tokens.TOKEN_MINUS){
            lastToken = getToken();
            currentToken = getToken();
            returnValue = factor();
            returnValue = new UnaryExpression(returnValue, lastToken == Tokens.TOKEN_PLUS ? null: null);

        } else {
            System.out.println("Illegal Tokens\n");
            throw new RuntimeException("Illegal Tokens");
        }
        return returnValue;
    }
}
