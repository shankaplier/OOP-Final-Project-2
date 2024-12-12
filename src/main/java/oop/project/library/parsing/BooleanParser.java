package oop.project.library.parsing;

/**
 * Parses a boolean from a string
 */
public class BooleanParser implements Parser<Boolean> {
    public Boolean parse(String input) throws ParseException {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
