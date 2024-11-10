package oop.project.library.parsing;

import oop.project.library.scenarios.Result;

public class IntegerParser {
    public static Result<Integer> parse(String input) {
        try {
            return new Result.Success<>(Integer.parseInt(input));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
