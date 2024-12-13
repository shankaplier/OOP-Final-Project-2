package oop.project.library.parsing;

/**
 * Parses an Integer from a String
 */
public class IntegerParser implements Parser<Integer> {
    public Integer parse(String input) throws ParseException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
