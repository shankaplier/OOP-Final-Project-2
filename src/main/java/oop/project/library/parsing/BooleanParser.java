package oop.project.library.parsing;

public class BooleanParser {
    public static Boolean parse(String input) {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
