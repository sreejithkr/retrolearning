package org.slang4;

import org.slang4.ast.*;
import org.slang4.ast.expression.*;
import org.slang4.ast.statement.AssignmentStatement;
import org.slang4.ast.statement.VariableDeclStatement;
import org.slang4.exception.SlangException;

import java.net.StandardSocketOptions;
import java.util.ArrayList;

public class RDParser extends Lexer {
    public RDParser(String expression) {
        super(expression);
    }

//    public Expression callExpression(){
//        currentToken = getToken();
//        return expression();
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
            case Tokens.TOKEN_VAR_STRING,
                 Tokens.TOKEN_VAR_BOOL,
                 Tokens.TOKEN_VAR_NUMBER:
                 returnValue = parseVariableDeclrataionStatement(context);
                getNextToken();
                break;
            case Tokens.TOKEN_UNQUOTED_STRING:
                returnValue = parseAssignmentStatement(context);
                getNextToken();
                break;
            case TOKEN_PRINT:
                returnValue = printStatement(context);
                getNextToken();
                break;
            case TOKEN_PRINTLN:
                returnValue = printLineStatement(context);
                getNextToken();
                break;
            default:
                getNextToken();
                returnValue = null;
               // throw new SlangException("Invalid statement",super.originalExpression,index);

        }
        return returnValue;
    }

    Statement printStatement(CompilationContext context){
        getNextToken();
        Expression expression = expression(context);
        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new SlangException(": is expected",originalExpression,index);
        }
        return new PrintStatement(expression);
    }

    public Statement printLineStatement(CompilationContext context){
        getNextToken();
        Expression expression = expression(context);
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
    public  Expression expression(CompilationContext context) {
        //System.out.println(inputExpression[index]);
        Expression returnValue = term(context);
        while (currentToken == Tokens.TOKEN_PLUS || currentToken == Tokens.TOKEN_MINUS){
            getNextToken();
            Expression el   = expression(context);
            if  (penultimateToken == Tokens.TOKEN_PLUS){
                returnValue = new BinaryPlus(returnValue,el);
            } else {
                returnValue = new BinaryMinus(returnValue,el);
            }
        }
        return returnValue;
    }

    public  Expression term(CompilationContext context){
        Tokens lastToken = currentToken;
        Expression returnValue = factor(context);
        while (currentToken == Tokens.TOKEN_DIV || currentToken == Tokens.TOKEN_MUL){
            lastToken = currentToken;
            getNextToken();
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
        Expression returnValue = null;
        if(currentToken == Tokens.TOKEN_OPAREN){
            getNextToken();
            returnValue   = expression(context);
            if (currentToken != Tokens.TOKEN_CPAREN){
                System.out.println("Missing Closing Parenthesis\n");
                throw new RuntimeException("Missing Closing Parenthesis");
            }
            getNextToken();

        } else if (currentToken == Tokens.TOKEN_STRING) {
            returnValue = new StringLiteral(lastDetectedString);
            getNextToken();
        } else if (currentToken == Tokens.TOKEN_UNQUOTED_STRING)
        {
            String str = lastDetectedString;
            SymbolInfo inf = context.getSymbolTable().get(str);
            if (inf == null){
                throw new SlangException("Undefined symbol: " + str,originalExpression,index);
            }
            getNextToken();
            returnValue = new Variable(inf);
        }
        else if(currentToken == Tokens.TOKEN_BOOL_FALSE || currentToken == Tokens.TOKEN_BOOL_TRUE) {
            returnValue = new BooleanConstant(currentToken == Tokens.TOKEN_BOOL_TRUE);
            getNextToken();

        }else if (currentToken == Tokens.TOKEN_NUMERIC){
            returnValue = new NumericExpression(number);
            getNextToken();
        } else if (currentToken == Tokens.TOKEN_PLUS || currentToken == Tokens.TOKEN_MINUS){
            Tokens lastToken = currentToken;
            getNextToken();
            returnValue = factor(context);
            if (lastToken == Tokens.TOKEN_PLUS){
                returnValue = new UnaryPlus(returnValue);
            } else {
                returnValue = new UnaryMinus(returnValue);
            }

        } else {
            System.out.println("Illegal Tokens\n");
            throw new SlangException("Illegal Tokens",originalExpression,index);
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
                 symbol.type = TypeInfo.numeric;
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
            throw new SlangException("Variable " + variableName + " not found",originalExpression,index);
        }
        getNextToken();
        Tokens token = currentToken;
        if (token != Tokens.TOKEN_ASSIGN){
            throw new SlangException("Illegal assignment, expected =",originalExpression,index);
        }

        getNextToken();
        Expression expression = expression(context);
        if (expression.typeCheck(context) != symbol.type){
            throw new SlangException("Illegal assignment,type mismatch",originalExpression,index);
        }

        if (currentToken != Tokens.TOKEN_SEMICOLON){
            throw new  SlangException("Missing semicolon",originalExpression,index);
        }

        return new AssignmentStatement(symbol, expression);
    }
}
