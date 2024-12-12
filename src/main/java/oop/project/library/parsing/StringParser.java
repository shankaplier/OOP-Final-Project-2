package oop.project.library.parsing;

/**
 * Parses a String from a String (it just returns the string that gets passed to it).
 */
public class StringParser implements Parser<String> {
    public String parse(String input) throws ParseException {
        return input;
    }
}
