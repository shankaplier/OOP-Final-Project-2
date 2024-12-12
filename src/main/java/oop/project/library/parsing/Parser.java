package oop.project.library.parsing;

/**
 * Represents a parser from a String to a T
 */
public interface Parser<T> {
    /**
     * @param input The String to be parsed
     * @return The parsed value
     * @throws ParseException If the parse fails.
     */
    T parse(String input) throws ParseException;
}
