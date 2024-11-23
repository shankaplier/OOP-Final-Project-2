package oop.project.library.parsing;

public class DoubleParser implements Parser<Double> {
    public Double parse(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
