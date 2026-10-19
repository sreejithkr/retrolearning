package org.slang4;

public class Lexer {

    private char[] inputExpression;
    Integer index;
    Integer length;
    double number; // Last grabbed number from the stream

    Lexer(String expression){
        this.inputExpression = expression.toCharArray();
        this.index = 0;
        this.length = expression.length();
    }

    Tokens getToken(){
        Tokens token = Tokens.ILLEGAL_TOKEN;

        while ( index < length && (inputExpression[index] != '\t' || inputExpression[index] != ' ')) {
            if (index == length) {
                return Tokens.TOKEN_NULL;
            }

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
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':

                    String temp = "";
                    while (index < length && isNumber(inputExpression[index])) {
                        temp += inputExpression[index];
                        index++;
                    }
                    number = Double.parseDouble(temp);
                    token = Tokens.TOKEN_NUMERIC;
                break;
                default:
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

