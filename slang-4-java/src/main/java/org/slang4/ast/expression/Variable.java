package org.slang4.ast.expression;

import org.slang4.*;

public class Variable implements Expression{
    private String name;
    private TypeInfo type;

    //stores the variable name
    public Variable(SymbolInfo symbolInfo) {
        this.name = symbolInfo.symbolName;

    }

    /// Creates a new symbol and puts into the symbol table
    /// and stores the key ( variable name )
    Variable(CompilationContext compilationContext, String name, double value) {
        SymbolInfo symbolInfo = new SymbolInfo();
        symbolInfo.type = TypeInfo.numeric;
        symbolInfo.symbolName = name;
        symbolInfo.dblVal = value;
        compilationContext.getSymbolTable().add(symbolInfo);
        this.name = name;
    }

    Variable(CompilationContext compilationContext, String name, String value) {
        SymbolInfo symbolInfo = new SymbolInfo();
        symbolInfo.type = TypeInfo.string;
        symbolInfo.symbolName = name;
        symbolInfo.strVal = value;
        compilationContext.getSymbolTable().add(symbolInfo);
        this.name = name;
    }

    Variable(CompilationContext compilationContext, String name, Boolean value) {
        SymbolInfo symbolInfo = new SymbolInfo();
        symbolInfo.type = TypeInfo.bool;
        symbolInfo.symbolName = name;
        symbolInfo.boolVal = value;
        compilationContext.getSymbolTable().add(symbolInfo);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }


    @Override
    public SymbolInfo evaluate(RuntimeContext context) {

        if (context.getSymbolTable() == null){
            return null;
        } else {
           return context.getSymbolTable().get(name);
        }
    }

    @Override
    public TypeInfo typeCheck(CompilationContext context) {
        if (context.getSymbolTable() == null){
            return TypeInfo.illegal;
        } else {
            return context.getSymbolTable().get(name).type;
        }
    }

    @Override
    public TypeInfo getType() {
        return type;
    }
}
