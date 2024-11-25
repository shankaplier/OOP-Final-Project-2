package oop.project.library.parsing;

public class IntegerParser implements Parser<Integer> {
    public Integer parse(String input) throws ParseException {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
