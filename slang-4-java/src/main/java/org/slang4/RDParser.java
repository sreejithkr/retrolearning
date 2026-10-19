package org.slang4;

import org.slang4.ast.*;

import java.util.ArrayList;

public class RDParser extends Lexer {
    public RDParser(String expression) {
        super(expression);
    }

    public Expression callExpression(){
        currentToken = getToken();
        return expresion();
    }


    /// <summary>
    /// The Grammar is
    ///
    /// <stmtlist> := { <statement> }+
    ///
    /// {<statement> := <printstmt> | <printlinestmt>
    /// <printstmt> := print <expr >;
    ///
    /// <printlinestmt>:= printline <expr>;
    ///
    /// <Expr> ::= <Term> | <Term> { + | - } <Expr>
    /// <Term> ::= <Factor> | <Factor> {*|/} <Term>
    /// <Factor>::= <number> | ( <expr> ) | {+|-} <factor>
    ///
    ///
    /// </summary>
    public ArrayList<Statement> parse(){
        getNextToken();
        return statementList();
    }

    public ArrayList<Statement> statementList(){
        ArrayList<Statement> statements = new ArrayList<>();
        //TODO Add statement while current token not null
        while (currentToken != Tokens.TOKEN_NULL){
            Statement statement = statement();
            if (statement != null) {
                statements.add(statement);
            }
        }
        return statements;
    }
    Statement statement(){
        Statement returnValue = null;
        switch (currentToken){
            case TOKEN_PRINT:
                returnValue = printStatement();
                getNextToken();
                break;
            case TOKEN_PRINTLN:
                returnValue = printLineStatement();
                getNextToken();
                break;
            default:
                throw new RuntimeException("Invalid statement");
        }
        return returnValue;
    }

    Statement printStatement(){
        getNextToken();
        Expression expression = expresion();
        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new RuntimeException(": is expected");
        }
        return new PrintStatement(expression);
    }

    public Statement printLineStatement(){
        getNextToken();
        Expression expression = expresion();
        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new RuntimeException(": is expected");
        }
        return new PrintLineStatement(expression);
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
