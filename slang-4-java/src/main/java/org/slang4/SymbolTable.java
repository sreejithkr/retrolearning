package org.slang4;

import org.slang4.ast.expression.Variable;

import java.util.HashMap;

public class SymbolTable {
    private final HashMap<String,Object> dt = new HashMap<>();


    public Boolean add(SymbolInfo symbolInfo) {
        dt.put(symbolInfo.symbolName, symbolInfo);
        return true;
    }

    public SymbolInfo get(String symbolName) {
        return (SymbolInfo) dt.get(symbolName);
    }

    public void assign(Variable variable, SymbolInfo value) {
        value.symbolName = variable.getName();
        assign(variable.getName(),value);
    }

    public void assign(String name, SymbolInfo value) {
        dt.put(name,value);
    }
}
