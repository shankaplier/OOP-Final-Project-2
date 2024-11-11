package oop.project.library.parsing;

import oop.project.library.scenarios.Result;

public class IntegerParser {
    public static Integer parse(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }
}
