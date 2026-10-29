package org.slang4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class RDParserTest {

    private RDParser parser;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {


    }

    @Test
    void shouldTestScriptA() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("scriptA.sl").toURI());
        String expression = Files.readString(path);
        //CompilationContext context = new CompilationContext(new SymbolTable());
        parser = new RDParser(expression);
        parser.parse();
        // TODO: verify a print statement produces a PrintStatement AST node
    }

    @Test
    void shouldTestScriptB() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("First.sl").toURI());
        String expression = Files.readString(path);
        //CompilationContext context = new CompilationContext(new SymbolTable());
        parser = new RDParser(expression);
        parser.parse();
        // TODO: verify a print statement produces a PrintStatement AST node
    }

    @Test
    void shouldTestScriptC() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("Second.sl").toURI());
        String expression = Files.readString(path);
        //CompilationContext context = new CompilationContext(new SymbolTable());
        parser = new RDParser(expression);
        parser.parse();
        // TODO: verify a print statement produces a PrintStatement AST node
    }

    @Test
    void shouldTestScriptD() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("third.sl").toURI());
        String expression = Files.readString(path);
        //CompilationContext context = new CompilationContext(new SymbolTable());
        parser = new RDParser(expression);
        parser.parse();
        // TODO: verify a print statement produces a PrintStatement AST node
    }


    @Test
    void shouldParsePrintLineStatement() {
        // TODO: verify a printline statement produces a PrintLineStatement AST node
    }

    @Test
    void shouldParseNumericExpression() {
        // TODO: verify a numeric literal produces a NumericExpression node
    }

    @Test
    void shouldParseBinaryAddition() {
        // TODO: verify "1+2" produces a BinaryPlus node
    }

    @Test
    void shouldThrowOnMissingSemicolon() {
        // TODO: verify RuntimeException is thrown when semicolon is absent
    }

    @Test
    void shouldParseVariableDeclaration() {
        // TODO: verify a variable declaration statement is added to the symbol table
    }
}
