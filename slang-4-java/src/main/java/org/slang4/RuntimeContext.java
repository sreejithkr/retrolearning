package org.slang4;

public class RuntimeContext {
    private SymbolTable symbolTable;

    public RuntimeContext(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
