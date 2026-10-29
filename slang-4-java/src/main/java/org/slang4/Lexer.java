package org.slang4;

import org.slang4.exception.SlangException;

import java.util.Arrays;
import java.util.Objects;

public class Lexer {

    protected String originalExpression;

    private char[] inputExpression;
    Integer index;
    Integer length;
    double number; // Last grabbed number from the stream
    protected ValueTable[] valueTables;
    Tokens currentToken;
    Tokens penultimateToken;
    String lastDetectedString;


    Lexer(String expression){
        originalExpression = expression;
        this.inputExpression = expression.toCharArray();
        this.index = 0;
        this.length = expression.length();
        valueTables =  new ValueTable[7];
        valueTables[0] = new ValueTable(Tokens.TOKEN_BOOL_FALSE, "FALSE");
        valueTables[1] = new ValueTable(Tokens.TOKEN_BOOL_TRUE, "TRUE");
        valueTables[2] = new ValueTable(Tokens.TOKEN_VAR_STRING, "STRING");
        valueTables[3] = new ValueTable(Tokens.TOKEN_VAR_BOOL, "BOOLEAN");
        valueTables[4] = new ValueTable(Tokens.TOKEN_VAR_NUMBER, "NUMERIC");
        valueTables[5] = new ValueTable(Tokens.TOKEN_PRINT, "PRINT");
        valueTables[6] = new ValueTable(Tokens.TOKEN_PRINTLN, "PRINTLINE");
    }

    protected Tokens getNextToken(){
        penultimateToken = currentToken;
        currentToken = getToken();
        return currentToken;
    }

    Tokens getToken(){
        Tokens token = Tokens.ILLEGAL_TOKEN;

        while( index < length && (inputExpression[index] == '\t' || inputExpression[index] == ' ')){
            index++;
        }
        if (index > length - 1)
            return Tokens.TOKEN_NULL;

            System.out.printf("test %d",index);

            switch (inputExpression[index]) {
                case '\n':
                    index++;
                    break;
                case '=':
                    token = Tokens.TOKEN_ASSIGN;
                    index++;
                    break;
                case ';':
                    token = Tokens.TOKEN_SEMICOLON;
                    index++;
                    break;
                case '+':
                    token = Tokens.TOKEN_PLUS;
                    index++;
                    break;
                case  '-':
                    token = Tokens.TOKEN_MINUS;
                    index++;
                    break;
                case '*':
                    token = Tokens.TOKEN_MUL;
                    index++;
                    break;
                case  '/':
                    if(inputExpression[index + 1] == '/') {
                        Boolean continueLoop = true;
                        do {
                            if (inputExpression[index] == '\n' || inputExpression[index] == '\r') {
                                continueLoop = false;
                            } else {
                                index++;
                            }
                        } while (index < length && continueLoop);
                        index++;
                    } else {
                        token = Tokens.TOKEN_DIV;
                        index++;
                    }
                    break;
                case  '(':
                    token = Tokens.TOKEN_OPAREN;
                    index++;
                    break;
                case  ')':
                    token = Tokens.TOKEN_CPAREN;
                    index++;
                    break;
                case '"':
                    StringBuilder stringVal = new StringBuilder();
                    token = Tokens.TOKEN_STRING;
                    index++;
                    while (index < length && inputExpression[index] != '"'){
                         stringVal.append(inputExpression[index]);
                         index++;
                     }
                    if (index >= length) {
                        token = Tokens.ILLEGAL_TOKEN;
                        return token;
                    } else {
                        lastDetectedString = stringVal.toString();
                        index++;
                    }
                    break;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':

                    StringBuilder temp = new StringBuilder();
                    while (index < length && isNumber(inputExpression[index])) {
                        temp.append(inputExpression[index]);
                        index++;
                    }
                    if (inputExpression[index] == '.') {
                        temp.append(inputExpression[index]);
                        index++;
                        while (index < length && isNumber(inputExpression[index])) {
                            temp.append(inputExpression[index]);
                            index++;
                        }
                    }
                    number = Double.parseDouble(temp.toString());
                    token = Tokens.TOKEN_NUMERIC;
                break;
                default:
                    if (Character.isAlphabetic(inputExpression[index]) || inputExpression[index] == '_'){
                        StringBuilder tempStr = new StringBuilder();
                        while (index < length && Character.isLetterOrDigit(inputExpression[index]) ) {
                            tempStr.append(inputExpression[index]);
                            index++;
                        }
                        lastDetectedString = tempStr.toString();

                        ValueTable valueTable = Arrays.stream(valueTables)
                                .filter(value -> value.value.equals(lastDetectedString))
                                .findFirst().orElse(null);
                        if (valueTable != null){
                            token = valueTable.tokens;
                        } else {
                            token = Tokens.TOKEN_UNQUOTED_STRING;
                        }
                    } else {
                        System.out.println("Error while analysing the token");
                        throw new SlangException("Error while analysing the token", originalExpression, index);
                    }
            }

        return token;
    }

    private Boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

}

