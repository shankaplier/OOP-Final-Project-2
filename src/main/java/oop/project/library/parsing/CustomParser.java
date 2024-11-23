package oop.project.library.parsing;

import java.util.function.Function;

public class CustomParser<T> implements Parser<T> {
    Function<String, T> parser;
    public CustomParser(Function<String, T> parser) {
        this.parser = parser;
    }

    @Override
    public T parse(String input) {
        try {
            return parser.apply(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
