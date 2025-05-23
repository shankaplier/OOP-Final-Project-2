package oop.project.library.parsing;

import java.util.function.Function;

/**
 * Parses a custom type using a provided function
 *
 * @param <T> The type that will be parsed
 */
public class CustomParser<T> implements Parser<T> {
    Function<String, T> parser;

    /**
     * Any RuntimeExceptions thrown will be caught converted to ParseExceptions.
     *
     * @param parser A function from a String to a T.
     */
    public CustomParser(Function<String, T> parser) {
        this.parser = parser;
    }

    @Override
    public T parse(String input) throws ParseException {
        try {
            return parser.apply(input);
        } catch (RuntimeException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
