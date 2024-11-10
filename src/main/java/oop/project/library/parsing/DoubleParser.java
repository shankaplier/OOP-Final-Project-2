package oop.project.library.parsing;

import oop.project.library.scenarios.Result;

public class DoubleParser {
    public static Result<Double> parse(String input) {
        try {
            return new Result.Success<>(Double.parseDouble(input));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
