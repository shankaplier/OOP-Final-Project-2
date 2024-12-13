package oop.project.library.lexer;

/**
 * Represents a failure to parse a string in the lexer
 */
public class LexerException extends RuntimeException {
    public LexerException(String message) {
        super(message);
    }
}
