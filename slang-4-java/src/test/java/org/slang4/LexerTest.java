package org.slang4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    private Lexer lexer;

    @BeforeEach
    void setUp() {
        // Initialize with a sample expression before each test
        lexer = new Lexer("");
    }

    @Test
    void shouldTokenizePlus() {
        // TODO: verify TOKEN_PLUS is returned for "+"
    }

    @Test
    void shouldTokenizeMinus() {
        // TODO: verify TOKEN_MINUS is returned for "-"
    }

    @Test
    void shouldTokenizeNumericLiteral() {
        // TODO: verify TOKEN_NUMERIC is returned and number field is set correctly
    }

    @Test
    void shouldTokenizeStringLiteral() {
        // TODO: verify TOKEN_STRING is returned and lastDetectedString is set
    }

    @Test
    void shouldReturnIllegalTokenForUnterminatedString() {
        // TODO: verify ILLEGAL_TOKEN is returned when closing quote is missing
    }
}
