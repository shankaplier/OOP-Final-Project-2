package oop.project.library.parsing;

import oop.project.library.scenarios.Result;

public class IntegerParser {
    public static Result<Integer> parse(String unparsedLeft) {
        try {
            return new Result.Success<>(Integer.parseInt(unparsedLeft));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
