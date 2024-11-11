package oop.project.library.parsing;

import oop.project.library.scenarios.Result;

public class DoubleParser {
    public static Double parse(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
