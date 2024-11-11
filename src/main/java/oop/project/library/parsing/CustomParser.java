package oop.project.library.parsing;

import java.util.function.Function;

public class CustomParser {
    public static <T> T parse(Function<String, T> parser, String input) {
        try {
            return parser.apply(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
