package org.slang4;

import java.util.Arrays;

public class Lexer {

    private char[] inputExpression;
    Integer index;
    Integer length;
    double number; // Last grabbed number from the stream
    protected ValueTable[] valueTables;
    Tokens currentToken;
    Tokens penultimateToken;
    String lastDetectedString;


    Lexer(String expression){
        this.inputExpression = expression.toCharArray();
        this.index = 0;
        this.length = expression.length();
        valueTables =   Arrays.stream(Tokens.values())
                .map(token -> new ValueTable(token, token.value()))
                .toArray(ValueTable[]::new);
    }

    protected Tokens getNextToken(){
        penultimateToken = currentToken;
        currentToken = getToken();
        return currentToken;
    }

    Tokens getToken(){
        Tokens token = Tokens.ILLEGAL_TOKEN;

        while ( index < length && (inputExpression[index] != '\t' || inputExpression[index] != ' ')) {
            switch (inputExpression[index]) {

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
                    token = Tokens.TOKEN_DIV;
                    index++;
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
                    }
                    System.out.println("Error while analysing the token");
                    throw new RuntimeException("Error while analysing the token");
            }
        }
        return token;
    }

    private Boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

}

