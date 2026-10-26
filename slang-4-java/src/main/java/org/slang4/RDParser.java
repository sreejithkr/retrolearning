package org.slang4;

import org.slang4.ast.*;
import org.slang4.ast.expression.*;
import org.slang4.ast.statement.AssignmentStatement;
import org.slang4.ast.statement.VariableDeclStatement;

import java.util.ArrayList;

public class RDParser extends Lexer {
    public RDParser(String expression) {
        super(expression);
    }

//    public Expression callExpression(){
//        currentToken = getToken();
//        return expresion();
//    }


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
        SymbolTable symbolTable = new SymbolTable();
        CompilationContext context = new CompilationContext(symbolTable);
        return statementList(context);
    }

    public ArrayList<Statement> statementList(CompilationContext context){
        ArrayList<Statement> statements = new ArrayList<>();
        //TODO Add statement while current token not null
        while (currentToken != Tokens.TOKEN_NULL){
            Statement statement = statement(context);
            if (statement != null) {
                statements.add(statement);
            }
        }
        return statements;
    }
    Statement statement(CompilationContext context){
        Statement returnValue = null;
        switch (currentToken){
            case TOKEN_PRINT:
                returnValue = printStatement(context);
                getNextToken();
                break;
            case TOKEN_PRINTLN:
                returnValue = printLineStatement(context);
                getNextToken();
                break;
            default:
                throw new RuntimeException("Invalid statement");
        }
        return returnValue;
    }

    Statement printStatement(CompilationContext context){
        getNextToken();
        Expression expression = expresion(context);
        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new RuntimeException(": is expected");
        }
        return new PrintStatement(expression);
    }

    public Statement printLineStatement(CompilationContext context){
        getNextToken();
        Expression expression = expresion(context);
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
    public  Expression expresion(CompilationContext context) {
        Tokens lastToken = getToken();
        Expression returnValue = term(context);
        while (currentToken == Tokens.TOKEN_PLUS || currentToken == Tokens.TOKEN_MINUS){
            lastToken = currentToken;
            currentToken = getToken();
            Expression el   = expresion(context);
            if  (lastToken == Tokens.TOKEN_PLUS){
                returnValue = new BinaryPlus(returnValue,el);
            } else if (lastToken == Tokens.TOKEN_MINUS){
                returnValue = new BinaryMinus(returnValue,el);
            }else {
                throw new RuntimeException("Invalid token");
            }
        }
        return returnValue;
    }

    public  Expression term(CompilationContext context){
        Tokens lastToken = getToken();
        Expression returnValue = factor(context);
        while (currentToken == Tokens.TOKEN_DIV || currentToken == Tokens.TOKEN_MUL){
            lastToken = currentToken;
            currentToken = getToken();
            Expression el   = term(context);
            if (lastToken == Tokens.TOKEN_DIV){
                returnValue = new BinaryDivision(returnValue,el);
            } else if(lastToken == Tokens.TOKEN_MUL){
                returnValue = new BinaryMultiply(returnValue,el);
            } else {
                throw new RuntimeException("Invalid token");
            }
        }
        return returnValue;
    }
//    <Factor>::= <number> | ( <expr> ) | {+|-} <factor>
//    <Factor>::= <number> | ( <expr> ) | {+|-} <factor> <variable> | TRUE | FALSE //Is the EBNF correct for variable ?
    public Expression factor(CompilationContext context){
        Tokens lastToken = getToken();
        Expression returnValue = null;
        if(lastToken == Tokens.TOKEN_OPAREN){
            lastToken = currentToken;
            returnValue   = expresion(context);
            if (lastToken != Tokens.TOKEN_CPAREN){
                System.out.println("Missing Closing Parenthesis\n");
                throw new RuntimeException("Missing Closing Parenthesis");
            }
            currentToken = getToken();

        } else if (lastToken == Tokens.TOKEN_UNQUOTED_STRING) {
            returnValue = new StringLiteral(lastDetectedString);
            currentToken = getToken();

        } else if(lastToken == Tokens.TOKEN_BOOL_FALSE || lastToken == Tokens.TOKEN_BOOL_TRUE) {
            returnValue = new BooleanConstant(lastToken == Tokens.TOKEN_BOOL_TRUE);
            currentToken = getToken();

        }else if (lastToken == Tokens.TOKEN_NUMERIC){
            returnValue = new NumericExpression(number);
        } else if (lastToken == Tokens.TOKEN_PLUS || lastToken == Tokens.TOKEN_MINUS){
            lastToken = getToken();
            currentToken = getToken();
            returnValue = factor(context);
            if (lastToken == Tokens.TOKEN_PLUS){
                returnValue = new UnaryPlus(returnValue);
            } else if (lastToken == Tokens.TOKEN_MINUS){
                returnValue = new UnaryMinus(returnValue);
            } else {
                throw new RuntimeException("Invalid token");
            }

        } else {
            System.out.println("Illegal Tokens\n");
            throw new RuntimeException("Illegal Tokens");
        }
        return returnValue;
    }

    public Statement parseVariableDeclrataionStatement(CompilationContext context) {
         Tokens token = currentToken;
         getNextToken();

         if (currentToken == Tokens.TOKEN_UNQUOTED_STRING){
             SymbolInfo symbol = new SymbolInfo();
             symbol.symbolName = lastDetectedString;
             if (token == Tokens.TOKEN_VAR_BOOL){
                 symbol.type = TypeInfo.bool;
             } else if (token == Tokens.TOKEN_VAR_STRING ) {
                 symbol.type = TypeInfo.string;
             } else if (token == Tokens.TOKEN_VAR_NUMBER){
                 symbol.type = TypeInfo.bool;
             }
             getNextToken();
             if (currentToken == Tokens.TOKEN_SEMICOLON){
                 context.getSymbolTable().add(symbol);
                 return new VariableDeclStatement(symbol);
             } else {
                 throw new RuntimeException("Semicolon expected");
             }

         } else {
             throw new RuntimeException("Illegal variable declaration\n");

         }

    }

    public Statement parseAssignmentStatement(CompilationContext context) {

        String variableName = lastDetectedString;
        SymbolInfo symbol = context.getSymbolTable().get(variableName);
        if (symbol == null) {
            throw new RuntimeException("Variable " + variableName + " not found");
        }

        getNextToken();
        if (currentToken != Tokens.TOKEN_ASSIGN){
            throw new RuntimeException("Illegal assignment, expected =");
        }

        getNextToken();
        Expression expression = expresion(context);
        if (expression.typeCheck(context) != symbol.type){
            throw new RuntimeException("Illegal assignment,type mismatch");
        }

        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new RuntimeException("Missing semicolon");
        }

        return new AssignmentStatement(symbol, expression);
    }
}
