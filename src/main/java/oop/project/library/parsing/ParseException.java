package oop.project.library.parsing;

/**
 * Represents a failure to parse a value from a String.
 */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}
